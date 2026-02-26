package com.gsantos.personalfinanceapi.dto.category;

import com.gsantos.personalfinanceapi.model.enums.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CategoryRequestDTO(
        @NotBlank
        String name,

        @NotNull
        TransactionType type
) {}
