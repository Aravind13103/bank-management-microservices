package com.example.transactionservice.controller;

import com.example.transactionservice.model.Transaction;
import com.example.transactionservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    // Deposit
    @PostMapping("/deposit")
    public ResponseEntity<Transaction> deposit(@RequestBody Map<String, String> request) {
        Transaction transaction = transactionService.deposit(
                request.get("accountNumber"),
                Double.parseDouble(request.get("amount")),
                request.get("description")
        );
        return ResponseEntity.ok(transaction);
    }

    // Withdraw
    @PostMapping("/withdraw")
    public ResponseEntity<Transaction> withdraw(@RequestBody Map<String, String> request) {
        Transaction transaction = transactionService.withdraw(
                request.get("accountNumber"),
                Double.parseDouble(request.get("amount")),
                request.get("description")
        );
        return ResponseEntity.ok(transaction);
    }

    // Transfer
    @PostMapping("/transfer")
    public ResponseEntity<Transaction> transfer(@RequestBody Map<String, String> request) {
        Transaction transaction = transactionService.transfer(
                request.get("fromAccount"),
                request.get("toAccount"),
                Double.parseDouble(request.get("amount")),
                request.get("description")
        );
        return ResponseEntity.ok(transaction);
    }

    // Get history of an account
    @GetMapping("/history/{accountNumber}")
    public ResponseEntity<List<Transaction>> getHistory(@PathVariable String accountNumber) {
        return ResponseEntity.ok(transactionService.getHistory(accountNumber));
    }

    // Get all transactions
    @GetMapping("/all")
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        return ResponseEntity.ok(transactionService.getAllTransactions());
    }

    // Health check
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Transaction Service is running!");
    }
}