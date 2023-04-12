package com.danmag.ecommerce.service.exceptions;

public class ProductAlreadyExistsException extends RuntimeException {
    public ProductAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
