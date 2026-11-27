package com.igor.AuthenticationJWT.exception.handler;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.igor.AuthenticationJWT.exception.custom.BadRequestException;
import com.igor.AuthenticationJWT.exception.custom.ConflictException;
import com.igor.AuthenticationJWT.exception.custom.NotFoundException;
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
public class ApplicationExceptionHandler {

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorMessage> handleConflictException(ConflictException ex, HttpServletRequest request){
        ErrorMessage errorMessage = new ErrorMessage(
                Instant.now(),
                409,
                "CONFLICT",
                ex.getMessage(),
                request.getRequestURI(),
                List.of()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
    }

    @ExceptionHandler({IllegalStateException.class, JWTCreationException.class})
    public ResponseEntity<ErrorMessage> handleInternalServerError(Exception ex, HttpServletRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(
                Instant.now(),
                500,
                "INTERNAL_SERVER_ERROR",
                "OCORREU UM ERRO INTERNO",
                request.getRequestURI(),
                List.of()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }

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

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorMessage> handleNotFoundException(NotFoundException ex, HttpServletRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(
                Instant.now(),
                404,
                "NOT_FOUND",
                ex.getMessage(),
                request.getRequestURI(),
                List.of()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorMessage> handleBadRequestException(BadRequestException ex, HttpServletRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(
                Instant.now(),
                400,
                "BAD_REQUEST",
                ex.getMessage(),
                request.getRequestURI(),
                List.of()
        );
        return ResponseEntity.badRequest().body(errorMessage);
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
