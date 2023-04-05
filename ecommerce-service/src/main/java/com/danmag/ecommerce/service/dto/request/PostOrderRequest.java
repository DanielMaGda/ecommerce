package com.danmag.ecommerce.service.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class PostOrderRequest {

    @NotBlank
    @Size(min = 3, max = 52)
    @Pattern(regexp = "^[a-zA-Z\\s]+$")
    private String shipName;

    @NotBlank
    @Size(min = 3, max = 240)
    @Pattern(regexp = "[0-9a-zA-Z #,-]+")
    private String shipAddress;

    @NotBlank
    @Size(min = 3, max = 240)
    @Pattern(regexp = "[0-9a-zA-Z #,-]+")
    private String billingAddress;

    @NotBlank
    @Size(min = 3, max = 100)
    @Pattern(regexp = "^[a-zA-Z\\s]+$")
    private String city;

    @NotBlank
    @Size(min = 3, max = 40)
    @Pattern(regexp = "^[a-zA-Z\\s]+$")
    private String state;

    @NotBlank
    @Size(min = 5, max = 6)
    @Pattern(regexp = "^[0-9]*$")
    private String zip;

    @NotBlank
    @Size(min = 3, max = 40)
    @Pattern(regexp = "^[a-zA-Z\\s]+$")
    private String country;
    @NotNull
    @Size(min = 2, max = 50)
    private String trackingNumber;
    @NotNull
    @Size(min = 2, max = 50)
    private String cargoFirm;

    @NotBlank
    @Size(min = 9, max = 12)
    @Pattern(regexp = "[0-9]+")
    private String phone;

}