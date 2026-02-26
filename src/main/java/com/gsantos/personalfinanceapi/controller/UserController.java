package com.gsantos.personalfinanceapi.controller;

import com.gsantos.personalfinanceapi.dto.user.UserRequestDTO;
import com.gsantos.personalfinanceapi.dto.user.UserResponseDTO;
import com.gsantos.personalfinanceapi.dto.user.UserUpdateDTO;
import com.gsantos.personalfinanceapi.model.entities.User;
import com.gsantos.personalfinanceapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers(){

        List<User> users = userService.findAllUsers();

        List<UserResponseDTO> response = users.stream().map(user -> new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail()
        )).toList();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/NewUser")
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

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable UUID id,
                                           @RequestBody @Valid UserUpdateDTO data) {

        User updateUser = userService.updateUser(id, data);

        UserResponseDTO response = new UserResponseDTO(
                updateUser.getId(),
                updateUser.getName(),
                updateUser.getEmail()
        );

        return  ResponseEntity.ok(response);
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
