package com.danmag.ecommerce.service.exceptions;

public class UserAccountNotFoundException extends RuntimeException {
    public UserAccountNotFoundException(String message) {
        super(message);
    }
}
