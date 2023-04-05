package com.danmag.ecommerce.service.dto;

import lombok.Data;

@Data
public class OrderItemsDto {

    private long id;
    private long amount;

    private OrderProductDto product;

}
