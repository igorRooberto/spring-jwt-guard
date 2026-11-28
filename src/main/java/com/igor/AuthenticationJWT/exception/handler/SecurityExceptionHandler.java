package com.igor.AuthenticationJWT.exception.handler;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.igor.AuthenticationJWT.exception.response.ErrorMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;

@RestControllerAdvice
public class SecurityExceptionHandler {

    @ExceptionHandler({BadCredentialsException.class, JWTVerificationException.class})
    public ResponseEntity<ErrorMessage> handleUnauthorizedExceptions(Exception ex, HttpServletRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(
                Instant.now(),
                401,
                "UNAUTHORIZED",
                ex.getMessage(),
                request.getRequestURI(),
                List.of()
        );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorMessage> handleAccessDenied(AccessDeniedException ex, HttpServletRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(
                Instant.now(),
                403,
                "FORBIDDEN",
                ex.getMessage(),
                request.getRequestURI(),
                List.of()
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorMessage);
    }

}
