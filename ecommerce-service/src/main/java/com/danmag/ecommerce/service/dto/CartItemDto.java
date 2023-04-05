package com.danmag.ecommerce.service.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class CartItemDto {
    private long id;

    private CartProductDto product;

    private Integer amount;
    @JsonBackReference

    private CartDto cart;

    @Override
    public String toString() {
        return "CartItemDto{" +
                "id=" + id +
                ", product=" + product +
                ", amount=" + amount +
                '}';
    }
}
