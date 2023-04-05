package com.danmag.ecommerce.service.dto.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class AddToCartRequest {
    @NotNull
    private long productId;

    @NotNull
    @Min(value = 1)
    private int amount;
}
