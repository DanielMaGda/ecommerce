package com.danmag.ecommerce.service.exceptions;

public class CartNotFoundException extends RuntimeException {

    public CartNotFoundException(String message ) {
        super(message);
    }
}

