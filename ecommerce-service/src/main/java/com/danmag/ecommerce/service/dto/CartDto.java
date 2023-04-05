package com.danmag.ecommerce.service.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CartDto {

    private long id;
    @JsonManagedReference
    private AccountDTO account;
    private float totalPrice;
    private float totalCartPrice;
    private float totalCargoPrice;
    @JsonManagedReference
    private List<CartItemDto> cartItemList;

    @Override
    public String toString() {
        return "CartDto{" +
                "id=" + id +
                ", totalPrice=" + totalPrice +
                ", cartItemList=" + cartItemList +
                '}';
    }
}
