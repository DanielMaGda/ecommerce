package com.danmag.ecommerce.service.exceptions;

public class BrandNotFoundException extends RuntimeException{
    public BrandNotFoundException(String message,Throwable cause){
        super(message,cause);
    }
}
