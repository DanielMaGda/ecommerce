package com.danmag.ecommerce.service.dto;

import lombok.Data;

@Data
public class CartItemDto {
private long id;

private CartProductDto product;

private Integer amount;
}
