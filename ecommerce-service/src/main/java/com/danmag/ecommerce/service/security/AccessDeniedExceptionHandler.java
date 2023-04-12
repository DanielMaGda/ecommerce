package com.danmag.ecommerce.service.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AccessDeniedExceptionHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException exc) throws IOException {

        String errorMessage =
                "Access Denied: You are not authorized to access this resource";
        response.sendError(HttpServletResponse.SC_FORBIDDEN, errorMessage);
    }
}

