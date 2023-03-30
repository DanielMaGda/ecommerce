package com.danmag.ecommerce.service.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductDTO {
    private long id;

    private String name;
    private BrandDTO brand;

    private long price;

    private CategoryDTO category;


    List<FeatureDTO> features;


}
