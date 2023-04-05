package com.danmag.ecommerce.service.dto.response;

import com.danmag.ecommerce.service.enums.Role;
import lombok.Data;

@Data
public class AccountResponse {
    private long id;
    private String email;
    private String userName;

    private Role role;

    private boolean isAccountNonExpired;

    private boolean isAccountNonLocked;


    private boolean isCredentialsNonExpired;

    private boolean isEnabled;
}
