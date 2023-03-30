package com.danmag.ecommerce.service.dto;

import lombok.Data;

@Data
public class FeatureDTO {
    private long id;
    private String fullName;
    private String shortName;
    private String description;


    private FeatureValueDTO featureValues;


}
