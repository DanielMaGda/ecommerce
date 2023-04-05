package com.danmag.ecommerce.service.service;

import com.danmag.ecommerce.service.dto.CartDto;
import com.danmag.ecommerce.service.dto.request.AddToCartRequest;
import com.danmag.ecommerce.service.dto.response.RemoveProductFromCartResponse;
import com.danmag.ecommerce.service.exceptions.ProductNotFoundException;
import com.danmag.ecommerce.service.exceptions.ProductOutOfStockException;
import com.danmag.ecommerce.service.model.Account;
import com.danmag.ecommerce.service.model.Cart;
import com.danmag.ecommerce.service.model.CartItem;
import com.danmag.ecommerce.service.model.Product;
import com.danmag.ecommerce.service.dto.request.RemoveFromCartRequest;
import com.danmag.ecommerce.service.dto.response.CartResponse;
import com.danmag.ecommerce.service.repository.CartItemRepository;
import com.danmag.ecommerce.service.repository.CartRepository;
import com.danmag.ecommerce.service.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final AccountsService accountsService;
    @Autowired
    private ModelMapper modelMapper;
    private final ProductRepository productRepository;

    private final CartItemRepository cartItemRepository;

    @Autowired
    public CartService(CartRepository cartRepository, AccountsService accountsService, ProductRepository productRepository, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.accountsService = accountsService;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
    }

    public List<CartDto> getAllCarts() {
        List<Cart> carts = cartRepository.findAll();
        return carts.stream().map(cart -> modelMapper.map(cart, CartDto.class)).toList();
    }


    public CartDto getCartByAccountId(long accountId) {
        Optional<Cart> cart = cartRepository.findByAccountId(accountId);
        return cart.map(c -> modelMapper.map(c, CartDto.class)).orElseThrow(() -> new NoSuchElementException("User with id " + accountId + " does not have any carts"));
    }

    public CartResponse addProductToCart(AddToCartRequest addToCartRequest) throws AccessDeniedException, ProductOutOfStockException {
        long productId = addToCartRequest.getProductId();
        int amount = addToCartRequest.getAmount();

        Optional<Account> account = accountsService.getAccount();
        if (account.isEmpty()) {
            throw new AccessDeniedException("User not authenticated");
        }

        Cart cart = account.get().getCart();
        if (cart == null) {
            cart = createCart(account.get());
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        int totalAmount = amount;
        for (CartItem cartItem : cart.getCartItemList()) {
            if (cartItem.getProduct().getId() == (productId)) {
                // Item already exists, update amount
                totalAmount += cartItem.getAmount();
            }
        }

        if (product.getStock() < totalAmount) {
            throw new ProductOutOfStockException("Product stock is less than requested amount.");
        }

        cart = addOrUpdateCartItem(cart, product, amount);
        cart = calculatePrice(cart); // Recalculate cart price
        cartRepository.save(cart); // Save

        return new CartResponse();
    }

    public Cart addOrUpdateCartItem(Cart cart, Product product, Integer amount) {
        List<CartItem> cartItemList = cart.getCartItemList();
        boolean itemFound = false;
        for (CartItem cartItem : cartItemList) {
            if (cartItem.getProduct().getId() == (product.getId())) {
                // Item already exists, update amount
                cartItem.setAmount(cartItem.getAmount() + amount);
                cartItemRepository.save(cartItem);
                itemFound = true;
                break;
            }
        }
        if (!itemFound) {
            // Item doesn't exist, add new item
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setAmount(amount);
            cartItem.setCart(cart);
            cartItemList.add(cartItem);
            cart.setCartItemList(cartItemList);
            cartItemRepository.save(cartItem);
        }
        return cartRepository.save(cart);
    }


    private Cart createCart(Account account) {
        Cart cart = new Cart();
        cart.setAccount(account);
        cartRepository.save(cart);
        return cart;
    }

    public RemoveProductFromCartResponse removeProductFromCart(RemoveFromCartRequest removeFromCartRequest) throws AccessDeniedException {

        Optional<Account> account = accountsService.getAccount();
        if (account.isPresent()) {
            Cart cart = account.get().getCart();
            if (cart == null) {
                throw new IllegalStateException("Cart not found");
            }
            if (cart.getCartItemList()
                    .removeIf(cartItem -> cartItem.getProduct()
                            .getId() == removeFromCartRequest.getProductId())) {
                Cart updatedCart = calculatePrice(cart);
                cartRepository.save(updatedCart);
                RemoveProductFromCartResponse response = new RemoveProductFromCartResponse();
                response.setProductId(removeFromCartRequest.getProductId());
                return response;
            } else {
                throw new IllegalStateException("Product not found in cart");
            }
        } else {
            throw new AccessDeniedException("User not authenticated");
        }
    }

    public Cart calculatePrice(Cart cart) {
        cart.setTotalCartPrice(0F);
        cart.setTotalCargoPrice(0F);
        cart.setTotalPrice(0F);

        cart.getCartItemList().forEach(cartItemDto -> cart.setTotalCartPrice(cart.getTotalCartPrice()
                + (cartItemDto.getProduct().getPrice()) * cartItemDto.getAmount()));

        float totalCargoPrice = updateCartTotalPrice(cart);
        cart.setTotalCargoPrice(totalCargoPrice);
        cart.setTotalPrice(cart.getTotalCartPrice() + totalCargoPrice);

        return cart;
    }

    public int getTotalProductAmount(Cart cart) {
        return cart.getCartItemList()
                .stream()
                .mapToInt(CartItem::getAmount)
                .sum();
    }

    public float updateCartTotalPrice(Cart cart) {
        int amount = getTotalProductAmount(cart);
        if (amount == 0) {
            return 0;
        } else {
            return (amount > 5) ? 0 : 15;
        }
    }


    public void emptyCart() throws AccessDeniedException {
        Optional<Account> account = accountsService.getAccount();
        if (account.isPresent()) {

            account.get().setCart(createCart(account.get()));
            accountsService.saveAccount(account);
        } else {
            throw new AccessDeniedException("user dont exist");
        }

    }
}

