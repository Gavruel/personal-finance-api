package com.gsantos.personalfinanceapi.controller;

import com.gsantos.personalfinanceapi.dto.category.CategoryRequestDTO;
import com.gsantos.personalfinanceapi.dto.category.CategoryResponseDTO;
import com.gsantos.personalfinanceapi.model.entities.Category;
import com.gsantos.personalfinanceapi.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/users/{userId}/categories")
    public ResponseEntity<CategoryResponseDTO> createCategory(
            @PathVariable UUID userId,
            @RequestBody @Valid CategoryRequestDTO data) {

        Category category = categoryService.createCategory(data, userId);

        CategoryResponseDTO response = new CategoryResponseDTO(
                category.getId(),
                category.getName(),
                category.getType(),
                category.getUser().getId()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/users/{userId}/categories")
    public ResponseEntity<List<CategoryResponseDTO>> listByUser(@PathVariable UUID userId) {
        List<Category> categories = categoryService.findByUserId(userId);

        List<CategoryResponseDTO> response = categories.stream()
                .map(c -> new CategoryResponseDTO(
                        c.getId(),
                        c.getName(),
                        c.getType(),
                        c.getUser().getId()
                )).toList();

        return ResponseEntity.ok(response);
    }
}
