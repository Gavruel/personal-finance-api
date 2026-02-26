package com.gsantos.personalfinanceapi.dto.user;

import jakarta.validation.constraints.Size;

public record UserUpdateDTO(
        String name,
        String email,
        @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
        String password
) {
}
