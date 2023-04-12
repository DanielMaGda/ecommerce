package com.danmag.ecommerce.service.dto.response;

import com.danmag.ecommerce.service.dto.CartItemDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartResponse {
    private List<CartItemDto> cartItemList;
    private double totalCartPrice;
    private double totalCargoPrice;
    private double totalPrice;

    @Override
    public String toString() {
        return "CartResponse{" +
                "cartItemList=" + cartItemList +
                ", totalCartPrice=" + totalCartPrice +
                ", totalCargoPrice=" + totalCargoPrice +
                ", totalPrice=" + totalPrice +
                '}';
    }


}
