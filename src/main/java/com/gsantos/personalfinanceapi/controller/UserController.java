package com.gsantos.personalfinanceapi.controller;

import com.gsantos.personalfinanceapi.dto.user.UserResponseDTO;
import com.gsantos.personalfinanceapi.dto.user.UserUpdateDTO;
import com.gsantos.personalfinanceapi.model.entities.User;
import com.gsantos.personalfinanceapi.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getMe(
            @AuthenticationPrincipal UserDetails userDetails ){

        User user = userService.findByEmail(userDetails.getUsername());

        return  ResponseEntity.ok(new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail()
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(
            @PathVariable UUID id,
            @RequestBody @Valid UserUpdateDTO data) {

        User updateUser = userService.updateUser(id, data);

        UserResponseDTO response = new UserResponseDTO(
                updateUser.getId(),
                updateUser.getName(),
                updateUser.getEmail()
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}