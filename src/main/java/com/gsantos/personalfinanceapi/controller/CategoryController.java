package com.gsantos.personalfinanceapi.controller;

import com.gsantos.personalfinanceapi.model.entities.Category;
import com.gsantos.personalfinanceapi.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<Category> save(
            @RequestBody Category category,
            @RequestParam String userEmail) {

        Category savedCategory = categoryService.createCategory(category, userEmail);
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Category>> listByUser(@RequestParam String userEmail) {

        List<Category> categories = categoryService.findByUserEmail(userEmail);
        return ResponseEntity.ok(categories);
    }
}
