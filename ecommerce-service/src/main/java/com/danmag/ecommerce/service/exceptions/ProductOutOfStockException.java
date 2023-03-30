package com.danmag.ecommerce.service.exceptions;

public class ProductOutOfStockException extends Exception {
    public ProductOutOfStockException(String message) {
        super(message);
    }
}
