package com.danmag.ecommerce.service.model.request;

import com.danmag.ecommerce.service.enums.Role;
import lombok.Data;

@Data
public class RegisterRequest {
    private String userName;
    private String email;
    private String password;

    private Role role;
}
