package com.igor.AuthenticationJWT.exception.response;

import java.time.Instant;
import java.util.List;

public record ErrorMessage(
        Instant timestamp,
        int status,
        String error,
        String message,
        String pathURI,
        List<FieldErrorMessage> details
) {
}
