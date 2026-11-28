package com.igor.AuthenticationJWT.exception.handler;

import com.igor.AuthenticationJWT.exception.response.ErrorMessage;
import com.igor.AuthenticationJWT.exception.response.FieldErrorMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

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
                400,
                "BAD_REQUEST",
                "Campos Inválidos",
                request.getRequestURI(),
                details
        );

        return ResponseEntity.badRequest().body(errorMessage);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorMessage> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, HttpServletRequest request){

        List<FieldErrorMessage> details = List.of(new FieldErrorMessage(ex.getName(), "Valor Inválido"));

        ErrorMessage errorMessage = new ErrorMessage(
                Instant.now(),
                400,
                "BAD_REQUEST",
                "Paramêtros Inválidos",
                request.getRequestURI(),
                details
        );

        return ResponseEntity.badRequest().body(errorMessage);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorMessage> handleConstraintViolation(ConstraintViolationException ex, HttpServletRequest request
    ) {
        List<FieldErrorMessage> details = ex.getConstraintViolations()
                .stream().map(error ->
                        new FieldErrorMessage(
                                error.getPropertyPath().toString(),
                                error.getMessage()
                )).toList();

        ErrorMessage errorMessage = new ErrorMessage(
                Instant.now(),
                400,
                "BAD_REQUEST",
                "Paramêtros Inválidos",
                request.getRequestURI(),
                details
        );

        return ResponseEntity.badRequest().body(errorMessage);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorMessage> handleMissingParameter(MissingServletRequestParameterException ex, HttpServletRequest request) {

        List<FieldErrorMessage> details = List.of(
                new FieldErrorMessage(
                        ex.getParameterName(),
                        "Parâmetro obrigatório não informado"
                )
        );

        ErrorMessage errorMessage = new ErrorMessage(
                Instant.now(),
                400,
                "BAD_REQUEST",
                "Paramêtros Obrigatórios Ausentes",
                request.getRequestURI(),
                details
        );

        return ResponseEntity.badRequest().body(errorMessage);
    }
}
