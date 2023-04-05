package com.danmag.ecommerce.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class CategoryDTO {
    private long id;
    @NotNull
    @Size(min = 2, max = 50)
    @Pattern(regexp = "^[A-Za-z0-9 /]+$")

    private String name;

    @JsonIgnore
    private List<FeatureDTO> features;

}
