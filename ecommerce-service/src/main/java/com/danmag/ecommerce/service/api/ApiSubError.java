package com.danmag.ecommerce.service.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

class ApiSubError {
    public ApiSubError() {
    }
}

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
class ApiValidationError extends ApiSubError {
    private String object;
    private String field;
    private Object rejectedValue;
    private String message;

    // public constructor for creating instances of this class
    public ApiValidationError(String object, String message) {
        this.object = object;
        this.message = message;
    }
}
