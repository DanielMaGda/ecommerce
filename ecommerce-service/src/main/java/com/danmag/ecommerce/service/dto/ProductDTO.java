package com.danmag.ecommerce.service.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class ProductDTO {
    private long id;
    @NotNull
    @Size(min = 1, max = 255)
    private String name;
    @Valid
    private BrandDTO brand;
    @NotNull
    @Min(value = 0)
    private long price;
    @Valid
    private CategoryDTO category;
    @NotNull
    @Min(value = 0)
    private Integer stock;
    @Valid

    private List<ProductFeatureDTO> features;
}
