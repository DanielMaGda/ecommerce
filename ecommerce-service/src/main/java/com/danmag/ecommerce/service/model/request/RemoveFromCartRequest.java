package com.danmag.ecommerce.service.model.request;

import lombok.Data;

@Data
public class RemoveFromCartRequest {
    private long productId;
    private int amount;
}
