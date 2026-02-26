package com.gsantos.personalfinanceapi.service;


import com.gsantos.personalfinanceapi.dto.UserUpdateDTO;
import com.gsantos.personalfinanceapi.model.entities.User;
import com.gsantos.personalfinanceapi.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public User saveUser(User user) {
        return repository.save(user);
    }

    public List<User> findAllUsers() {
        return repository.findAll();
    }

    public User findByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("Email not found")
        );
    }

    @Transactional
    public User updateUser(UUID id, UserUpdateDTO data) {

        User user = repository.findById(id).orElseThrow(
                () -> new RuntimeException("User not found")
        );

        user.setName(data.name());
        user.setEmail(data.email());

        if (data.password() != null && !data.password().isBlank()) {
            user.setPassword(data.password());
        }

        return repository.save(user);
    }

    @Transactional
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
