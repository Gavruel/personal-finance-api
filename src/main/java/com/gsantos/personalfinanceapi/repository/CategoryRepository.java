package com.gsantos.personalfinanceapi.repository;

import com.gsantos.personalfinanceapi.model.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
}
