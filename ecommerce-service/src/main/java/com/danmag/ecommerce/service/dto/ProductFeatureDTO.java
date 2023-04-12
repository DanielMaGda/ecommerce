package com.danmag.ecommerce.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductFeatureDTO {
    private long id;
    @Valid
    private FeatureDTO feature;
    @Valid
    private FeatureValueDTO featureValue;


}
