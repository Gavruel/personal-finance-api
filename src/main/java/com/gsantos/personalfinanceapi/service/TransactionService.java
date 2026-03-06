package com.gsantos.personalfinanceapi.service;

import com.gsantos.personalfinanceapi.dto.transaction.TransactionRequestDTO;
import com.gsantos.personalfinanceapi.exception.ResourceNotFoundException;
import com.gsantos.personalfinanceapi.model.entities.Category;
import com.gsantos.personalfinanceapi.model.entities.Transaction;
import com.gsantos.personalfinanceapi.model.entities.User;
import com.gsantos.personalfinanceapi.repository.CategoryRepository;
import com.gsantos.personalfinanceapi.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;
    private final UserService userService;

    public TransactionService(TransactionRepository transactionRepository,
                              CategoryRepository categoryRepository,
                              UserService userService) {
        this.transactionRepository = transactionRepository;
        this.categoryRepository = categoryRepository;
        this.userService = userService;
    }

    @Transactional
    public Transaction createTransaction(TransactionRequestDTO data, UUID userId) {

        User user = userService.findById(userId);

        Category category = categoryRepository.findById(data.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Category not found with id: " + data.categoryId()));

        if (!category.getUser().getId().equals(userId)) {
            throw new ResourceNotFoundException(
                    "Category not found for this user");
        }

        Transaction transaction = new Transaction();
        transaction.setDescription(data.description());
        transaction.setAmount(data.amount());
        transaction.setDate(data.date());
        transaction.setType(data.type());
        transaction.setUser(user);
        transaction.setCategory(category);

        return transactionRepository.save(transaction);
    }

    @Transactional
    public List<Transaction> findTransactionByUserId(UUID userId) {
        userService.findById(userId);
        return transactionRepository.findAllByUserId(userId);
    }
}
