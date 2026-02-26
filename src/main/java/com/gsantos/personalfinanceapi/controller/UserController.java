package com.gsantos.personalfinanceapi.controller;

import com.gsantos.personalfinanceapi.dto.UserRequestDTO;
import com.gsantos.personalfinanceapi.dto.UserResponseDTO;
import com.gsantos.personalfinanceapi.dto.UserUpdateDTO;
import com.gsantos.personalfinanceapi.model.entities.User;
import com.gsantos.personalfinanceapi.repository.UserRepository;
import com.gsantos.personalfinanceapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
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
    public ResponseEntity<User> updateUser(@PathVariable UUID id,
                                           @RequestBody @Valid UserUpdateDTO data) {

        Optional<User> userOptional = userService.findById(id);

        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = userOptional.get();

        user.setName(data.name());
        user.setEmail(data.email());

        if (data.password() != null && !data.password().isBlank()) {
            user.setPassword(data.password());
        }

        userService.saveUser(user);

        return ResponseEntity.ok(user);
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
