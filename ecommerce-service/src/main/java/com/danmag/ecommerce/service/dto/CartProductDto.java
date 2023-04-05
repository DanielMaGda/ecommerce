package com.danmag.ecommerce.service.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)

public class CartProductDto extends ProductDetailsDto {

    private Integer stock;

}
