package com.danmag.ecommerce.service.dto.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class RemoveFromCartRequest {
    @NotNull

    private long productId;
    @NotNull
    @Min(1)
    private int amount;
}
