package com.gsantos.personalfinanceapi.service;

import com.gsantos.personalfinanceapi.model.entities.Category;
import com.gsantos.personalfinanceapi.model.entities.User;
import com.gsantos.personalfinanceapi.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;



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
    public Category createCategory(Category category, String userEmail) {
        User user = userService.findByEmail(userEmail);
        category.setUser(user);
        return categoryRepository.save(category);
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
