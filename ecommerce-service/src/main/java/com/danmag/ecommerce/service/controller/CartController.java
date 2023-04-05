package com.danmag.ecommerce.service.controller;

import com.danmag.ecommerce.service.api.ApiResponse;
import com.danmag.ecommerce.service.dto.CartDto;
import com.danmag.ecommerce.service.dto.request.RemoveFromCartRequest;
import com.danmag.ecommerce.service.dto.response.RemoveProductFromCartResponse;
import com.danmag.ecommerce.service.service.CartService;
import com.danmag.ecommerce.service.dto.request.AddToCartRequest;
import com.danmag.ecommerce.service.dto.response.CartResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.nio.file.AccessDeniedException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/v1/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CartDto>>> getAllCarts() {
        try {
            List<CartDto> response = cartService.getAllCarts();
            if (response.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Success", response));


        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ApiResponse<CartDto>> getById(@PathVariable("id") long id) {

        try {
            CartDto cartDto = cartService.getCartByAccountId(id);
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Success", cartDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CartResponse>> addProductToCart(@Valid @RequestBody AddToCartRequest addToCartRequest) {
        try {
            CartResponse cartResponse = cartService.addProductToCart(addToCartRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(HttpStatus.CREATED.value(), "Product added to Cart", cartResponse));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        }
    }

    @DeleteMapping()
    public ResponseEntity<ApiResponse<RemoveProductFromCartResponse>> deleteProductFromCart(@Valid @RequestBody RemoveFromCartRequest removeFromCartRequest) throws AccessDeniedException {
        try {
            RemoveProductFromCartResponse response = cartService.removeProductFromCart(removeFromCartRequest);
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Success", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        }
    }


}
