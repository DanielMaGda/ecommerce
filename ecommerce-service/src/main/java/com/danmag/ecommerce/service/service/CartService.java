package com.danmag.ecommerce.service.service;

import com.danmag.ecommerce.service.model.Cart;
import com.danmag.ecommerce.service.model.CartItem;
import com.danmag.ecommerce.service.dto.CartDto;
import com.danmag.ecommerce.service.model.Account;
import com.danmag.ecommerce.service.model.Product;
import com.danmag.ecommerce.service.model.request.CartResponse;
import com.danmag.ecommerce.service.repository.AccountsRepository;
import com.danmag.ecommerce.service.repository.CartRepository;
import com.danmag.ecommerce.service.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final AccountsRepository accountsRepository;
    private final AccountsService accountsService;
    @Autowired
    private ModelMapper modelMapper;
    private final ProductRepository productRepository;

    private final ProductService productService;

    @Autowired
    public CartService(CartRepository cartRepository, AccountsRepository accountsRepository, AccountsService accountsService, ProductRepository productRepository, ProductService productService) {
        this.cartRepository = cartRepository;
        this.accountsRepository = accountsRepository;
        this.accountsService = accountsService;
        this.productRepository = productRepository;
        this.productService = productService;
    }

    public List<CartDto> getAllCarts() {
        List<Cart> cartList = cartRepository.findAll();
        List<CartDto> cartDTOS = new ArrayList<>();
        for (Cart cart : cartList) {
            cartDTOS.add(modelMapper.map(cart, CartDto.class));
        }
        return cartDTOS;
    }

    public CartDto getCartByUserId(long userId) {
        Optional<Cart> cart = cartRepository.findByAccountId(userId);
        if (cart.isPresent()) {

            return modelMapper.map(cart.get(), CartDto.class);
        }
        throw new NoSuchElementException("User with id" + userId + " dont have any carts");


    }


//        public CartResponse addProductToCart(long productId, int amount) throws Exception {
//        Account account = accountsService.getUser();
//        Cart cart = account.getCart();
//        //Update Cart
//        if (Objects.nonNull(cart) && Objects.nonNull(cart.getCartItemList()) && !cart.getCartItemList().isEmpty()) {
//            Optional<CartItem> cartItem = cart.getCartItemList()
//                    .stream()
//                    .filter(ci -> ci.getProduct().getId() == (productId)).findFirst();
//            if (cartItem.isPresent()) {
//                if (cartItem.get().getProduct().getStock() < (cartItem.get().getAmount() + amount)) {
//                    throw new InvalidArgumentException("Product does not have desired stock.");
//                }
//                cartItem.get().setAmount(cartItem.get().getAmount() + amount);
//                //calculatePrice(cart);
//                //cart.setCartItemList((List<CartItem>) cartItem.orElse(new CartItem()));
//                cart = cartRepository.save(cart);
//                return new CartResponse();
//            }
//        }
//
//        //Create is not exist
//
//        if (Objects.isNull(cart)) {
//            cart = createCart(account);
//        }
//        Optional<Product> product = productRepository.findById(productId);
//        if (product.isPresent()) {
//            if (product.get().getStock() < amount) {
//                throw new InvalidArgumentException("Product does not have desired stock.");
//            }
//            CartItem cartItem = new CartItem();
//            cartItem.setAmount(amount);
//            cartItem.setProduct(product.get());
//            cartItem.setCart(cart);
//            if (Objects.isNull(cart.getCartItemList())) {
//                cart.setCartItemList(new ArrayList<>());
//            }
//            cart.getCartItemList().add(cartItem);
//            cart = cartRepository.save(cart);
//            return new CartResponse();
//
//        }
//
//        return null;
//
    public CartResponse addProductToCart(long productId, int amount) throws ProductOutOfStockException, AccessDeniedException {
        Account account = accountsService.getUser();
        Cart cart = account.getCart();

        Optional<CartItem> cartItem = findCartItemByProductId(cart, productId);

        if (cartItem.isPresent()) {
            if (isProductOutOfStock(cartItem.get().getProduct(), cartItem.get().getAmount() + amount)) {
                throw new ProductOutOfStockException("Product does not have desired stock.");
            }
            cartItem.get().setAmount(cartItem.get().getAmount() + amount);
        } else {
            addNewCartItem(cart, productId, amount);
        }

        cartRepository.save(cart);

        return new CartResponse();
    }

    private Optional<CartItem> findCartItemByProductId(Cart cart, long productId) {
        return cart.getCartItemList().stream()
                .filter(ci -> ci.getProduct().getId() == productId)
                .findFirst();
    }

    private void addNewCartItem(Cart cart, long productId, int amount) throws ProductOutOfStockException {
        Product product = getProductById(productId);
        if (isProductOutOfStock(product, amount)) {
            throw new ProductOutOfStockException("Product does not have desired stock.");
        }
        CartItem newItem = new CartItem(amount, product, cart);
        cart.getCartItemList().add(newItem);
    }

    private Product getProductById(long productId) throws ProductNotFoundException {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found."));
    }

    private boolean isProductOutOfStock(Product product, int amount) {
        return product.getStock() < amount;
    }

    public class ProductOutOfStockException extends Exception {
        public ProductOutOfStockException(String message) {
            super(message);
        }
    }

    public class ProductNotFoundException extends RuntimeException {
        public ProductNotFoundException(String message) {
            super(message);
        }
    }


    public void removeProductFromCart(long userId, double totalPrice) {
        Cart cart = new Cart();
        cart.setAccount(accountsRepository.findById(userId).orElse(null));
        cart.setTotalPrice(totalPrice);
        cartRepository.save(cart);
    }

    private Cart createCart(Account account) {
        Cart cart = new Cart();
        cart.setAccount(account);
        return cart;
    }
}
