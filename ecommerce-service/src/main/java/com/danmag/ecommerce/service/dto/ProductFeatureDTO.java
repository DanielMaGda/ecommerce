package com.danmag.ecommerce.service.dto;

import lombok.Data;

@Data
public class ProductFeatureDTO {
    private long id;

    private FeatureDTO feature;

    private FeatureValueDTO featureValue;


}
