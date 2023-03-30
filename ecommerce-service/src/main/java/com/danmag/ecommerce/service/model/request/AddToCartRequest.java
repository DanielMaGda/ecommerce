package com.danmag.ecommerce.service.model.request;

import lombok.Data;

@Data
public class AddToCartRequest {
    private long productId;
    private int amount;
}
