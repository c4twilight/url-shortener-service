package com.example.myapplication.exception;

import com.example.myapplication.common.error.ApiError;
import com.example.myapplication.covid.exception.BadRequestException;
import com.example.myapplication.covid.exception.ElementNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> handleBadRequestException(BadRequestException ex, HttpServletRequest request) {
        return buildError(HttpStatus.BAD_REQUEST, ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(ElementNotFoundException.class)
    public ResponseEntity<ApiError> handleElementNotFoundException(ElementNotFoundException ex, HttpServletRequest request) {
        return buildError(HttpStatus.NOT_FOUND, ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(ShortUrlNotFoundException.class)
    public ResponseEntity<ApiError> handleShortUrlNotFoundException(ShortUrlNotFoundException ex, HttpServletRequest request) {
        return buildError(HttpStatus.NOT_FOUND, ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> handleRuntimeException(RuntimeException ex, HttpServletRequest request) {
        log.error("Unexpected runtime exception", ex);
        return buildError(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", request.getRequestURI());
    }

    private ResponseEntity<ApiError> buildError(HttpStatus status, String message, String path) {
        ApiError error = new ApiError(
                Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                path
        );
        return ResponseEntity.status(status).body(error);
    }
}
