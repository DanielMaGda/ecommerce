package com.danmag.ecommerce.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeatureDTO {
    private long id;
    @NotNull
    @Size(min = 2, max = 50)
    @Pattern(regexp = "^[A-Za-z0-9\\s]+$")
    private String fullName;

    @NotNull
    @Size(min = 2, max = 30)
    @Pattern(regexp = "^[A-Za-z0-9\\s]+$")
    private String shortName;

    @Size(max = 255)
    @Pattern(regexp = "^[A-Za-z0-9\\s]+$")
    private String description;


}
