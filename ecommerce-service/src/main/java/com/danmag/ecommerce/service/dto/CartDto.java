package com.danmag.ecommerce.service.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {

    private long id;
    @JsonManagedReference
    private AccountDTO account;
    private double totalPrice;
    private double totalCartPrice;
    private double totalCargoPrice;
    @JsonManagedReference
    private List<CartItemDto> cartItemList;

    @Override
    public String toString() {
        return "CartDto{" +
                "id=" + id +
                ", totalPrice=" + totalPrice +
                ", totalCartPrice=" + totalCartPrice +
                ", totalCargoPrice=" + totalCargoPrice +
                ", cartItemList=" + cartItemList +
                '}';
    }
}
