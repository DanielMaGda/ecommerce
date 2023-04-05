package com.danmag.ecommerce.service.dto.response;

import com.danmag.ecommerce.service.dto.CartItemDto;
import lombok.Data;

import java.util.List;

@Data
public class CartResponse {
    private List<CartItemDto> cartItems;
    private Float totalCartPrice;
    private Float totalCargoPrice;
    private Float totalPrice;
}
