package com.danmag.ecommerce.service.dto.request;

import com.danmag.ecommerce.service.dto.ProductFeatureDTO;
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
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateProductRequest {
    private long id;
    @NotNull
    @Size(min = 1, max = 255)
    private String name;
    @NotNull
    @Min(0)
    private double price;
    @NotNull
    @Min(0)
    private Integer stock;
    @Valid

    private List<ProductFeatureDTO> features;
}
