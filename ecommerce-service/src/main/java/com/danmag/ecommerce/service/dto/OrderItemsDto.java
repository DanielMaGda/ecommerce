package com.danmag.ecommerce.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemsDto {
    private String name;
    private Float price;
    private Integer amount;
}
