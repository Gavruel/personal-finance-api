package com.gsantos.personalfinanceapi.exception;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponseDTO(
        int status,
        String error,
        String message,
        String path,
        LocalDateTime timestamp,
        List<FieldErrorDTO> fieldErrors
) {
    // Constructor for simple errors (no field errors)
    public ErrorResponseDTO(int status, String error, String message, String path) {
        this(status, error, message, path, LocalDateTime.now(), null);
    }

    // Constructor for validation errors (with field errors)
    public ErrorResponseDTO(int status, String error, String message, String path, List<FieldErrorDTO> fieldErrors) {
        this(status, error, message, path, LocalDateTime.now(), fieldErrors);
    }

    public record FieldErrorDTO(String field, String message) {}
}