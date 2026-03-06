package com.gsantos.personalfinanceapi.dto.transaction;

import com.gsantos.personalfinanceapi.model.enums.TransactionType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record TransactionRequestDTO(
        @NotNull(message = "Description is required")
        String description,

        @NotNull(message = "Amount is required")
        @DecimalMin(value = "0.01", message = "Amount must be greater than zero")
        BigDecimal amount,

        @NotNull(message = "Date is required")
        LocalDate date,

        @NotNull(message = "Transaction type is required")
        TransactionType type,

        @NotNull(message = "Category ID is required")
        UUID categoryId
) {}
