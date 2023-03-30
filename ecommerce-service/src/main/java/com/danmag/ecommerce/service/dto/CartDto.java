package com.danmag.ecommerce.service.dto;

import lombok.Data;

import java.util.List;

@Data
public class CartDto {

    private long id;


    private AccountDTO account;
    private double totalPrice;


    private List<CartItemDto> cartItemList;

}
