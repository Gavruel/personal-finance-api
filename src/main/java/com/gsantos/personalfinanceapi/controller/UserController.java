package com.gsantos.personalfinanceapi.controller;

import com.gsantos.personalfinanceapi.dto.UserRequestDTO;
import com.gsantos.personalfinanceapi.dto.UserResponseDTO;
import com.gsantos.personalfinanceapi.model.entities.User;
import com.gsantos.personalfinanceapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody @Valid UserRequestDTO data) {
        User newUser = new User();
        newUser.setName(data.name());
        newUser.setEmail(data.email());
        newUser.setPassword(data.password());

        User savedUser = userService.saveUser(newUser);

        UserResponseDTO response = new UserResponseDTO(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail()
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
