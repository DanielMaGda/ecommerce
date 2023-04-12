package com.danmag.ecommerce.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {
    private long id;
    @NotNull
    @Size(min = 1, max = 255)
    private String name;
    @Valid
    private BrandDTO brand;
    @NotNull
    @Min(0)
    private double price;
    @Valid
    private CategoryDTO category;
    @Size(max = 5000)
    private String description;
    @NotNull
    @Min(0)
    private Integer stock;
    @Valid
    private List<ProductFeatureDTO> features;
}
