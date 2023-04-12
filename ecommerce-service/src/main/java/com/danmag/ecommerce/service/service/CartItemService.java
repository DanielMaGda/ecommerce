package com.danmag.ecommerce.service.service;

import com.danmag.ecommerce.service.exceptions.CartItemNotFoundException;
import com.danmag.ecommerce.service.model.Cart;
import com.danmag.ecommerce.service.model.CartItem;
import com.danmag.ecommerce.service.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class CartItemService {
    private final CartItemRepository cartItemRepository;

    private final ProductService productService;


    @Autowired
    public CartItemService(CartItemRepository cartItemRepository, ProductService productService) {
        this.cartItemRepository = cartItemRepository;
        this.productService = productService;
    }

    public CartItem addOrUpdateCartItem(@Valid Cart cart, long productId, int amount) throws IllegalArgumentException {

        CartItem cartItem = getCartItemByCartAndProduct(cart, productId);
        int newAmount = cartItem != null ? cartItem.getAmount() + amount : amount;
        productService.checkProductStock(productId, newAmount);

        if (cartItem != null) {
            cartItem.setAmount(newAmount);

        } else {
            cartItem = CartItem.builder()
                    .cart(cart)
                    .product(productService.fetchProduct(productId))
                    .amount(amount)
                    .build();
            cart.getCartItemList().add(cartItem);

        }
        cartItemRepository.save(cartItem);
        return cartItem;

    }


    public List<CartItem> removeProductFromCart(@Valid Cart cart, long productId) {
        CartItem cartItem = getCartItemByCartAndProduct(cart, productId);
        if (cartItem != null) {
            cart.getCartItemList().remove(cartItem);
            return cart.getCartItemList();
        } else {
            throw new CartItemNotFoundException("Product not found in cart");
        }
    }

    public CartItem getCartItemByCartAndProduct(@Valid Cart cart, long productId)   {
        Optional<CartItem> optionalCartItem = cart.getCartItemList().stream()
                .filter(ci -> ci.getProduct().getId() == productId)
                .findFirst();
        if (optionalCartItem.isPresent()) {
            return optionalCartItem.get();
        } else {
            throw new CartItemNotFoundException("Cart item not found for product ID " + productId);
        }
    }



}

