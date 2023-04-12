package com.danmag.ecommerce.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ProductDetailsDto {
    private long id;
    private String name;
    private double price;

    private Integer stock;


}
