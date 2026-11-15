package com.igor.AuthenticationJWT.exception.message;

import java.util.List;

public record ValidationErrorMessage(
        int status,
        String error,
        String message,
        String pathURI,
        List<FieldErrorMessage> details
) {
}
