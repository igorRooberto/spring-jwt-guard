package com.igor.AuthenticationJWT.exception.message;

public record FieldErrorMessage(

        String field,
        String message
) {
}
