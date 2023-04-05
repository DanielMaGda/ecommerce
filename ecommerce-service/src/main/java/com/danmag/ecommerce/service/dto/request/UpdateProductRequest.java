package com.danmag.ecommerce.service.dto.request;

import com.danmag.ecommerce.service.dto.ProductFeatureDTO;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class UpdateProductRequest {
    private long id;
    @NotNull
    @Size(min = 1, max = 255)
    private String name;
    @NotNull
    @Min(value = 0)
    private long price;
    @NotNull
    @Min(value = 0)
    private Integer stock;
    @Valid

    private List<ProductFeatureDTO> features;
}
