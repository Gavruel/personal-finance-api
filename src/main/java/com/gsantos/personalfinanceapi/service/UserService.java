package com.gsantos.personalfinanceapi.service;


import com.gsantos.personalfinanceapi.model.entities.User;
import com.gsantos.personalfinanceapi.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    public Optional<User> findById(UUID id) {
        return repository.findById(id);
    }

    public User updateUser(User user) {
        return repository.save(user);
    }

    @Transactional
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
