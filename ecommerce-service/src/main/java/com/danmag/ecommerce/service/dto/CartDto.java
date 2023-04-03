package com.danmag.ecommerce.service.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CartDto {

    private long id;
    private double totalPrice;

    @Override
    public String toString() {
        return "CartDto{" +
                "id=" + id +
                ", totalPrice=" + totalPrice +
                ", cartItemList=" + cartItemList +
                '}';
    }

    @JsonManagedReference

    private List<CartItemDto> cartItemList;
    @JsonManagedReference
    private AccountDTO account;

}
