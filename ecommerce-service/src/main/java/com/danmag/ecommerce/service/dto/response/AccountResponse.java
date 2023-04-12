package com.danmag.ecommerce.service.dto.response;

import com.danmag.ecommerce.service.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
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
