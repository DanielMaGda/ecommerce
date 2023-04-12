package com.danmag.ecommerce.service.exceptions;

public class AuthenticationManagerException extends RuntimeException {
    public AuthenticationManagerException(String message, Throwable cause) {
        super(message, cause);
    }
}