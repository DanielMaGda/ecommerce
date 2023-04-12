package com.danmag.ecommerce.service.exceptions;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
