package com.gsantos.personalfinanceapi.repository;

import com.gsantos.personalfinanceapi.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);
    Optional<User> findById(UUID id);
    void deleteByEmail(String email);
}
