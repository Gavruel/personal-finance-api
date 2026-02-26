package com.gsantos.personalfinanceapi.repository;

import com.gsantos.personalfinanceapi.model.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {

    List<Category> findAllByUserEmail(String email);
    Optional<Category> findById(UUID uuid);
    List<Category> findAllByUserId(UUID userId);
}
