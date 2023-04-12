package com.danmag.ecommerce.service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddToCartRequest {
    @NotNull
    private long productId;

    @NotNull
    @Min(1)
    private int amount;
}
