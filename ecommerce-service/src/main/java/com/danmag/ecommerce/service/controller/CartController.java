package com.danmag.ecommerce.service.controller;

import com.danmag.ecommerce.service.api.ApiResponse;
import com.danmag.ecommerce.service.dto.CartDto;
import com.danmag.ecommerce.service.model.request.RemoveFromCartRequest;
import com.danmag.ecommerce.service.service.CartService;
import com.danmag.ecommerce.service.model.request.AddToCartRequest;
import com.danmag.ecommerce.service.model.response.CartResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllCarts() {
        List<CartDto> cartList = cartService.getAllCarts();
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK.value(), "Success", cartList), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ApiResponse> getById(@PathVariable("id") long id) {
        CartDto cartDto = cartService.getCartByAccountId(id);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK.value(), "succ", cartDto), HttpStatus.OK);
    }

    @PostMapping
    public CartResponse addProductToCart(@RequestBody AddToCartRequest addToCartRequest) throws Exception {

        return cartService.addProductToCart(addToCartRequest.getProductId(), addToCartRequest.getAmount());

    }

    @DeleteMapping()
    public ResponseEntity<ApiResponse> deleteProductFromCart(@RequestBody RemoveFromCartRequest removeFromCartRequest) throws AccessDeniedException {
        cartService.removeProductFromCart(removeFromCartRequest);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK.value(), "Success"), HttpStatus.OK);
    }
}
