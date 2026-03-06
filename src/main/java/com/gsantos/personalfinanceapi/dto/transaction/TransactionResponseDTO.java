package com.gsantos.personalfinanceapi.dto.transaction;

import com.gsantos.personalfinanceapi.model.entities.Category;
import com.gsantos.personalfinanceapi.model.entities.User;
import com.gsantos.personalfinanceapi.model.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record TransactionResponseDTO(
        UUID uuid,
        String description,
        BigDecimal amount,
        LocalDate date,
        TransactionType type,
        UUID userId,
        UUID categoryId,
        String categoryName
) { }
