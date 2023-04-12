package com.danmag.ecommerce.service.service;

import com.danmag.ecommerce.service.dto.CartDto;
import com.danmag.ecommerce.service.dto.CartItemDto;
import com.danmag.ecommerce.service.dto.request.AddToCartRequest;
import com.danmag.ecommerce.service.dto.response.CartResponse;
import com.danmag.ecommerce.service.exceptions.CartNotFoundException;
import com.danmag.ecommerce.service.model.Account;
import com.danmag.ecommerce.service.model.Cart;
import com.danmag.ecommerce.service.model.CartItem;
import com.danmag.ecommerce.service.model.Product;
import com.danmag.ecommerce.service.repository.CartRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final AccountService accountService;
    private final ProductService productService;
    private final ModelMapper modelMapper;
    private final CartItemService cartItemService;

    public CartService(CartRepository cartRepository, AccountService accountService, ProductService productService, ModelMapper modelMapper, CartItemService cartItemService) {
        this.cartRepository = cartRepository;
        this.accountService = accountService;
        this.productService = productService;
        this.modelMapper = modelMapper;
        this.cartItemService = cartItemService;
    }

    public List<CartDto> getAllCarts() {
        List<Cart> cartList = cartRepository.findAll();
        if (cartList.isEmpty()) {
            throw new CartNotFoundException("No carts available");
        }
        return cartList.stream().map(cart -> modelMapper.map(cart, CartDto.class)).toList();
    }


    public CartDto getCartByAccountId(long accountId) {
        Cart cart = cartRepository.findByAccountId(accountId)
                .orElseThrow(() -> new CartNotFoundException("User with id " + accountId + " does not have any carts"));
        return modelMapper.map(cart, CartDto.class);
    }

    public CartResponse getCartByAccountContext() {


        Account account = accountService.getCurrentUserAccount();
        Cart cart = getOrCreateCartForAccount(account);
        List<CartItemDto> cartItemDtoList = cart.getCartItemList().stream()
                .map(cartItem -> modelMapper.map(cartItem, CartItemDto.class))
                .toList();
        return CartResponse.builder()
                .cartItemList(cartItemDtoList)
                .totalCartPrice(cart.getTotalCartPrice())
                .totalCargoPrice(cart.getTotalCargoPrice())
                .totalPrice(cart.getTotalPrice())
                .build();
    }

    public CartResponse addProductToCart(AddToCartRequest addToCartRequest) {

        int amount = addToCartRequest.getAmount();

        Account account = accountService.getCurrentUserAccount();
        Cart cart = getOrCreateCartForAccount(account);

        Product product = retrieveProduct(addToCartRequest.getProductId());

        cartItemService.addOrUpdateCartItem(cart, product.getId(), amount);

        updateCartPrices(cart);
        saveCart(cart);

        return createCartResponse(cart);

    }


    public CartResponse removeProductFromCart(Long productId) {
        Account account = accountService.getCurrentUserAccount();
        Cart cart = getOrCreateCartForAccount(account);

        Product product = retrieveProduct(productId);

        cart.setCartItemList(cartItemService.removeProductFromCart(cart, product.getId()));

        updateCartPrices(cart);
        saveCart(cart);

        return createCartResponse(cart);
    }

    private void saveCart(Cart cart) {
        cartRepository.save(cart);
    }

    private Cart getOrCreateCartForAccount(Account account) {
        return cartRepository.findByAccount(account)
                .orElseGet(() -> createCart(account));
    }


    private Product retrieveProduct(Long productId) {
        return productService.fetchProduct(productId);
    }


    private Cart updateCartPrices(Cart cart) {
        if (cart == null || cart.getCartItemList() == null) {
            throw new CartNotFoundException("Cart is empty");
        }

        double totalCartPrice = calculateTotalCartPrice(cart);
        double totalCargoPrice = updateCartTotalPrice(cart);

        cart.setTotalCartPrice(totalCartPrice);
        cart.setTotalCargoPrice(totalCargoPrice);
        cart.setTotalPrice(totalCartPrice + totalCargoPrice);

        return cart;
    }

    private double calculateTotalCartPrice(Cart cart) {
        return cart.getCartItemList().stream()
                .mapToDouble(cartItem -> cartItem.getProduct().getPrice() * cartItem.getAmount())
                .sum();
    }


    private int getTotalProductAmount(Cart cart) {
        return cart.getCartItemList().stream().mapToInt(CartItem::getAmount).sum();
    }

    private double updateCartTotalPrice(Cart cart) {
        int amount = getTotalProductAmount(cart);
        if (amount == 0) {
            return 0;
        } else {
            return (amount <= 5) ? 15 : 0;
        }
    }

    protected void emptyCart() {
        Account account = accountService.getCurrentUserAccount();
        account.setCart(createCart(account));
        accountService.saveAccount(account);
    }


    private Cart createCart(Account account) {
        Cart cart = new Cart();
        List<CartItem> cartItemList = new ArrayList<>();
        cart.setCartItemList(cartItemList);
        cart.setAccount(account);
        cartRepository.saveAndFlush(cart);
        return cart;
    }

    private CartResponse createCartResponse(Cart cart) {
        List<CartItemDto> cartItemDtoList = cart.getCartItemList().stream()
                .map(cartItem -> modelMapper.map(cartItem, CartItemDto.class))
                .toList();
        return CartResponse.builder()
                .cartItemList(cartItemDtoList)
                .totalCartPrice(cart.getTotalCartPrice())
                .totalCargoPrice(cart.getTotalCargoPrice())
                .totalPrice(cart.getTotalPrice())
                .build();
    }

}

