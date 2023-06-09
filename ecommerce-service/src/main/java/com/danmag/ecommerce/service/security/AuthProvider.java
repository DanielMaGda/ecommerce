package com.danmag.ecommerce.service.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.ArrayList;

public class AuthProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = authentication.getName();
        String password = authentication.getCredentials().toString();


        return new UsernamePasswordAuthenticationToken(
                userName, password, new ArrayList<>()
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
