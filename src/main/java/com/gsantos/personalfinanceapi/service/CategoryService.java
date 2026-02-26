package com.gsantos.personalfinanceapi.service;

import com.gsantos.personalfinanceapi.dto.category.CategoryRequestDTO;
import com.gsantos.personalfinanceapi.model.entities.Category;
import com.gsantos.personalfinanceapi.model.entities.User;
import com.gsantos.personalfinanceapi.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;


@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserService userService;

    public CategoryService(CategoryRepository categoryRepository,
                           UserService userService) {
        this.categoryRepository = categoryRepository;
        this.userService = userService;
    }

    @Transactional
    public Category createCategory(CategoryRequestDTO data, UUID userId) {
        User user = userService.findById(userId);

        Category category = new Category();
        category.setName(data.name());
        category.setType(data.type());
        category.setUser(user);

        return categoryRepository.save(category);
    }

    @Transactional
    public List<Category> findCategoryByUserId(UUID userId) {
        List<Category> categories = categoryRepository.findAllByUserId(userId);

        return categories;
    }



    @Transactional
    public List<Category> findByUserEmail(String email) {
        List<Category> categories = categoryRepository.findAllByUserEmail(email);

        if (categories.isEmpty()) {
            throw new RuntimeException("No categories found for this user");
        }
        return categories;
    }
}
