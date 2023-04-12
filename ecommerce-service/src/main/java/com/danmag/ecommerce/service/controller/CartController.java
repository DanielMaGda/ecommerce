package com.danmag.ecommerce.service.controller;

import com.danmag.ecommerce.service.api.ApiResponse;
import com.danmag.ecommerce.service.dto.CartDto;
import com.danmag.ecommerce.service.dto.request.AddToCartRequest;
import com.danmag.ecommerce.service.dto.request.RemoveFromCartRequest;
import com.danmag.ecommerce.service.dto.response.CartResponse;
import com.danmag.ecommerce.service.exceptions.CartNotFoundException;
import com.danmag.ecommerce.service.exceptions.InvalidQuantityException;
import com.danmag.ecommerce.service.exceptions.ProductNotFoundException;
import com.danmag.ecommerce.service.exceptions.ProductOutOfStockException;
import com.danmag.ecommerce.service.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

@CrossOrigin
@RestController
@RequestMapping("api/v1/carts")
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
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CartDto>> getById(@PathVariable("id") long id) {
        try {
            CartDto cartResponse = cartService.getCartByAccountId(id);
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Success", cartResponse));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(HttpStatus.NOT_FOUND.value(), e.getMessage(), null));
        }
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse<CartResponse>> getCartOfCurrentAccount() {
        try {
            CartResponse cartDto = cartService.getCartByAccountContext();
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Success", cartDto));
        } catch (CartNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(HttpStatus.NOT_FOUND.value(), e.getMessage(), null));
        }
    }


    @PostMapping
    public ResponseEntity<ApiResponse<CartResponse>> addProductToCart(@Valid @RequestBody AddToCartRequest addToCartRequest) {
        try {
            CartResponse cartResponse = cartService.addProductToCart(addToCartRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(HttpStatus.CREATED.value(), "Product added to Cart", cartResponse));

        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(HttpStatus.NOT_FOUND.value(), e.getMessage(), null));
        } catch (InvalidQuantityException | ProductOutOfStockException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse<>(HttpStatus.UNAUTHORIZED.value(), e.getMessage(), null));
        }
    }


    @DeleteMapping()
    public ResponseEntity<ApiResponse<CartResponse>> deleteProductFromCart(@Valid @RequestBody RemoveFromCartRequest removeFromCartRequest) {
        try {
            CartResponse response = cartService.removeProductFromCart(removeFromCartRequest.getProductId());
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Success", response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null));
        }
    }


}
