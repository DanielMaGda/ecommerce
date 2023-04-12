package com.danmag.ecommerce.service.exceptions;

public class ProductOutOfStockException extends RuntimeException  {
    public ProductOutOfStockException(String message) {
        super(message);
    }
}
