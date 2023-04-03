package com.danmag.ecommerce.service.service;

import com.danmag.ecommerce.service.dto.CartDto;
import com.danmag.ecommerce.service.model.Account;
import com.danmag.ecommerce.service.model.Cart;
import com.danmag.ecommerce.service.model.CartItem;
import com.danmag.ecommerce.service.model.Product;
import com.danmag.ecommerce.service.model.request.RemoveFromCartRequest;
import com.danmag.ecommerce.service.model.response.CartResponse;
import com.danmag.ecommerce.service.repository.CartItemRepository;
import com.danmag.ecommerce.service.repository.CartRepository;
import com.danmag.ecommerce.service.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    //TODO Exceptions Validation
    public List<CartDto> getAllCarts() {
        List<Cart> carts = cartRepository.findAll();
        return carts.stream().map(cart -> modelMapper.map(cart, CartDto.class)).toList();
    }


    public CartDto getCartByAccountId(long accountId) {
        Optional<Cart> cart = cartRepository.findByAccountId(accountId);
        return cart.map(c -> modelMapper.map(c, CartDto.class))
                .orElseThrow(() -> new NoSuchElementException("User with id " + accountId + " does not have any carts"));
    }
    //TODO OptionalAccounts

    //TODO make response for CartResponse
    public CartResponse addProductToCart(Long productId, Integer amount) throws AccessDeniedException {
        if (productId == null || productId < 1) {
            throw new IllegalArgumentException("Invalid product ID");
        }
        if (amount == null || amount < 1) {
            throw new IllegalArgumentException("Invalid product amount");
        }
        Account account = accountsService.getUser();
        Cart cart = account.getCart();
        if (cart == null) {
            cart = createCart(account);
        }
        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Product not found"));
        addOrUpdateCartItem(cart, product, amount);
        return new CartResponse();
    }

    @Transactional
    public void addOrUpdateCartItem(Cart cart, Product product, Integer amount) {
        for (CartItem cartItem : cart.getCartItemList()) {
            if (cartItem.getProduct().getId() == (product.getId())) {
                // Item already exists, update amount
                cartItem.setAmount(cartItem.getAmount() + amount);
                cartItemRepository.save(cartItem);
                return;
            }
        }

        // Item does not exist, create new cart item
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setAmount(amount);
        cartItem.setCart(cart);

        cart.getCartItemList().add(cartItem);

        cartRepository.save(cart);
    }


    private Cart createCart(Account account) {
        Cart cart = new Cart();
        cart.setAccount(account);
        cartRepository.save(cart);
        return cart;
    }

    @Transactional
    public CartResponse removeProductFromCart(RemoveFromCartRequest removeFromCartRequest) throws AccessDeniedException {
        // Input validation
        if (removeFromCartRequest.getAmount() < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }

        Account account = accountsService.getUser();
        Cart cart = account.getCart();

        // Use orElseThrow instead of returning null
        if (cart == null) {
            throw new IllegalStateException("Cart not found");
        }

        cart.getCartItemList().stream()
                .filter(cartItem -> cartItem.getProduct().getId() == (removeFromCartRequest.getProductId()))
                .findFirst()
                .ifPresent(cartItem -> {
                    // Item already exists, update amount
                    if (cartItem.getAmount() > 1) {
                        cartItem.setAmount(cartItem.getAmount() - removeFromCartRequest.getAmount());
                        cartItemRepository.save(cartItem);
                    } else {
                        cart.getCartItemList().remove(cartItem);
                        cartRepository.save(cart);
                    }
                });

        return new CartResponse();
    }

}

