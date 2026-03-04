package com.gsantos.personalfinanceapi.dto.user;

public record CreateUserDTO(
        String name,
        String email,
        String password
) {}
