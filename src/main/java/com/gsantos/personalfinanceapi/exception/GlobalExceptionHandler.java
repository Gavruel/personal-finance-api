package com.gsantos.personalfinanceapi.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 404 - Resource not found
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request) {

        ErrorResponseDTO error = new ErrorResponseDTO(
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    // 409 - Resource already exists (duplicate)
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDTO> handleAlreadyExists(
            ResourceAlreadyExistsException ex,
            HttpServletRequest request) {

        ErrorResponseDTO error = new ErrorResponseDTO(
                HttpStatus.CONFLICT.value(),
                "Conflict",
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    // 422 - Bean Validation errors (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        List<ErrorResponseDTO.FieldErrorDTO> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(f -> new ErrorResponseDTO.FieldErrorDTO(f.getField(), f.getDefaultMessage()))
                .toList();

        ErrorResponseDTO error = new ErrorResponseDTO(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Validation Error",
                "One or more fields are invalid",
                request.getRequestURI(),
                fieldErrors
        );

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
    }

    // 409 - Database constraint violation (e.g. duplicate email or category name)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseDTO> handleDataIntegrity(
            DataIntegrityViolationException ex,
            HttpServletRequest request) {

        String message = "Data integrity violation. Check for duplicate values.";

        // Detect common constraint violations and give a friendlier message
        if (ex.getMessage() != null) {
            String msg = ex.getMessage().toLowerCase();
            if (msg.contains("users") && msg.contains("email")) {
                message = "This email is already in use.";
            } else if (msg.contains("categories") && msg.contains("name")) {
                message = "A category with this name already exists for this user.";
            }
        }

        ErrorResponseDTO error = new ErrorResponseDTO(
                HttpStatus.CONFLICT.value(),
                "Conflict",
                message,
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    // 500 - Unexpected errors
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGeneric(
            Exception ex,
            HttpServletRequest request) {

        ErrorResponseDTO error = new ErrorResponseDTO(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                "An unexpected error occurred. Please try again later.",
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}