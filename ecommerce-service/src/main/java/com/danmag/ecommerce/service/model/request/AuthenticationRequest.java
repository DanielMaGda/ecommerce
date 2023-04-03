package com.danmag.ecommerce.service.model.request;

import lombok.Data;

@Data
public class AuthenticationRequest {
    private String userName;
    String password;
}
