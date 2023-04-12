package com.danmag.ecommerce.service.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data

public class CartItemDto {
    private long id;

    private ProductDetailsDto product;

    @JsonBackReference

    private CartDto cart;
    private Integer amount;

    @Override
    public String toString() {
        return "CartItemDto{" +
                "id=" + id +
                ", product=" + product +
                ", amount=" + amount +
                '}';
    }
}
