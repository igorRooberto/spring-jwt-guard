package com.igor.AuthenticationJWT.exception.handler;

import com.igor.AuthenticationJWT.exception.response.ErrorMessage;
import com.igor.AuthenticationJWT.exception.response.FieldErrorMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;

@RestControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpServletRequest request){

        List<FieldErrorMessage> details = ex.getBindingResult().getFieldErrors()
                .stream().map(f -> new FieldErrorMessage(f.getField(), f.getDefaultMessage())).toList();

        ErrorMessage errorMessage = new ErrorMessage(
                Instant.now(),
                422,
                "BAD_REQUEST",
                "Campos Inválidos",
                request.getRequestURI(),
                details
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

}
