package com.gsantos.personalfinanceapi.service;

import com.gsantos.personalfinanceapi.dto.user.UserUpdateDTO;
import com.gsantos.personalfinanceapi.exception.ResourceAlreadyExistsException;
import com.gsantos.personalfinanceapi.exception.ResourceNotFoundException;
import com.gsantos.personalfinanceapi.model.entities.User;
import com.gsantos.personalfinanceapi.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository,
                       PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User saveUser(User user) {
        if (repository.findByEmail(user.getEmail()).isPresent()) {
            throw new ResourceAlreadyExistsException("Email already in use: " + user.getEmail());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    public List<User> findAllUsers() {
        return repository.findAll();
    }

    public User findByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
    }

    @Transactional
    public User updateUser(UUID id, UserUpdateDTO data) {
        User user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        if (data.name() != null && !data.name().isBlank()) {
            user.setName(data.name());
        }

        if (data.email() != null && !data.email().isBlank()) {
            user.setEmail(data.email());
        }

        if (data.password() != null && !data.password().isBlank()) {
            user.setPassword(passwordEncoder.encode(data.password()));
        }

        return repository.save(user);
    }

    @Transactional
    public User findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    @Transactional
    public void deleteById(UUID id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        repository.deleteById(id);
    }
}