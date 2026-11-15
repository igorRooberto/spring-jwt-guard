package com.igor.AuthenticationJWT.exception.message;

public record ErrorMessage(
        int status,
        String error,
        String message,
        String pathURI
) {
}
