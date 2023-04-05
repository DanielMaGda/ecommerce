package com.danmag.ecommerce.service.dto;

import lombok.Data;

import javax.validation.Valid;

@Data
public class ProductFeatureDTO {
    private long id;
    @Valid
    private FeatureDTO feature;
    @Valid
    private FeatureValueDTO featureValue;


}
