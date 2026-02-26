package com.gsantos.personalfinanceapi.dto.category;

import com.gsantos.personalfinanceapi.model.entities.User;
import com.gsantos.personalfinanceapi.model.enums.TransactionType;

import java.util.UUID;

public record CategoryResponseDTO(
        UUID id,
        String name,
        TransactionType type,
        User userId
) {}
