package com.danmag.ecommerce.service.dto;

import lombok.Data;

@Data
public class CartProductDto {
    private long id;
    private String name;
    private Integer stock;
}
