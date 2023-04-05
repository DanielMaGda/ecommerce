package com.danmag.ecommerce.service.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class AccountLoginDTO {
    private long id;
    @NotNull
    @Size(min = 3, max = 20)
    @Pattern(regexp = "^[a-zA-Z0-9]+$")
    private String userName;
    @NotNull
    @Email
    private String email;
    @NotNull
    @Size(min = 8, max = 75)
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$")

    private String password;

}
