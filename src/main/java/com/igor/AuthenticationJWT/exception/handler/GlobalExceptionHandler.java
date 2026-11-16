package com.igor.AuthenticationJWT.exception.handler;

import com.igor.AuthenticationJWT.exception.ConflictException;
import com.igor.AuthenticationJWT.exception.message.ErrorMessage;
import com.igor.AuthenticationJWT.exception.message.FieldErrorMessage;
import com.igor.AuthenticationJWT.exception.message.ValidationErrorMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorMessage> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpServletRequest request){

        List<FieldErrorMessage> details = ex.getBindingResult().getFieldErrors()
                .stream().map(f -> new FieldErrorMessage(f.getField(), f.getDefaultMessage())).toList();

        ValidationErrorMessage errorMessage = new ValidationErrorMessage(
                422,
                "BAD_REQUEST",
                "Campos Inválidos",
                request.getRequestURI(),
                details
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorMessage> handleConflictException(ConflictException ex, HttpServletRequest request){

        ErrorMessage errorMessage = new ErrorMessage(
                409,
                "CONFLICT",
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorMessage> handleIllegalState(IllegalStateException ex, HttpServletRequest request){
        ErrorMessage errorMessage = new ErrorMessage(
                500,
                "INTERNAL_SERVER_ERROR",
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorMessage> handleBadCredentials(BadCredentialsException ex, HttpServletRequest request){
        ErrorMessage errorMessage = new ErrorMessage(
                401,
                "UNAUTHORIZED",
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
    }


}
