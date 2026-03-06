package com.gsantos.personalfinanceapi.controller;

import com.gsantos.personalfinanceapi.dto.transaction.TransactionRequestDTO;
import com.gsantos.personalfinanceapi.dto.transaction.TransactionResponseDTO;
import com.gsantos.personalfinanceapi.model.entities.Transaction;
import com.gsantos.personalfinanceapi.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("users/{userId}")
    public ResponseEntity<TransactionResponseDTO> register(
            @PathVariable UUID transactionId,
            @RequestBody TransactionRequestDTO data) {

        Transaction transaction = transactionService.createTransaction(data, transactionId);

        TransactionResponseDTO response = new TransactionResponseDTO(
                transaction.getId(),
                transaction.getDescription(),
                transaction.getAmount(),
                transaction.getDate(),
                transaction.getType(),
                transaction.getUser().getId(),
                transaction.getCategory().getId(),
                transaction.getCategory().getName()
                );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("users/{userId}")
    public ResponseEntity<List<TransactionResponseDTO>> getAllTransactions(@PathVariable UUID userId) {

        List<Transaction> transactions = transactionService.findTransactionByUserId(userId);

        List<TransactionResponseDTO> response = transactions.stream().map(transaction ->
                new TransactionResponseDTO(
                        transaction.getId(),
                        transaction.getDescription(),
                        transaction.getAmount(),
                        transaction.getDate(),
                        transaction.getType(),
                        transaction.getUser().getId(),
                        transaction.getCategory().getId(),
                        transaction.getCategory().getName()
                )
        ).toList();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
