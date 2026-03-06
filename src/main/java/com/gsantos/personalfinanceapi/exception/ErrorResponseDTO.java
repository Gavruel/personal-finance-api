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

    public ErrorResponseDTO(int status, String error, String message, String path) {
        this(status, error, message, path, LocalDateTime.now(), null);
    }


    public ErrorResponseDTO(int status, String error, String message, String path, List<FieldErrorDTO> fieldErrors) {
        this(status, error, message, path, LocalDateTime.now(), fieldErrors);
    }

    public record FieldErrorDTO(String field, String message) {}
}