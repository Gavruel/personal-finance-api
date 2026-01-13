package com.gsantos.personalfinanceapi.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UserResponseDTO(
        UUID id,
        String name,
        String email
) {}
