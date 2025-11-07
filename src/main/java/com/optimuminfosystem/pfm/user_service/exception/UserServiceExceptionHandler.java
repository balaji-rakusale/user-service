package com.optimuminfosystem.pfm.user_service.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class UserServiceExceptionHandler {
    @ExceptionHandler(UserServiceException.class)
    public ResponseEntity<Map<String, Object>> handleServiceException(UserServiceException ex) {
        return ResponseEntity.status(ex.getStatus())
                .body(Map.of(
                        "timestamp", LocalDateTime.now(),
                        "errorCode", ex.getErrorCode(),
                        "errorMessage", ex.getErrorMessage()
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneric(Exception ex) {
        return ResponseEntity.internalServerError()
                .body(Map.of(
                        "timestamp", LocalDateTime.now(),
                        "errorCode", "INTERNAL_ERROR",
                        "errorMessage", ex.getMessage()
                ));
    }
}
