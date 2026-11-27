package com.igor.AuthenticationJWT.exception.response;

public record FieldErrorMessage(

        String field,
        String message
) {
}
