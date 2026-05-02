package com.example.accountservice.controller;

import com.example.accountservice.model.Account;
import com.example.accountservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // Create account
    @PostMapping("/create")
    public ResponseEntity<Account> createAccount(@RequestBody Map<String, String> request) {
        Account account = accountService.createAccount(
                Long.parseLong(request.get("userId")),
                request.get("accountType")
        );
        return ResponseEntity.ok(account);
    }

    // Get account by account number
    @GetMapping("/{accountNumber}")
    public ResponseEntity<Account> getAccount(@PathVariable String accountNumber) {
        return ResponseEntity.ok(accountService.getAccount(accountNumber));
    }

    // Get all accounts by userId
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Account>> getAccountsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(accountService.getAccountsByUserId(userId));
    }

    // Deposit
    @PostMapping("/deposit")
    public ResponseEntity<Account> deposit(@RequestBody Map<String, String> request) {
        Account account = accountService.deposit(
                request.get("accountNumber"),
                Double.parseDouble(request.get("amount"))
        );
        return ResponseEntity.ok(account);
    }

    // Withdraw
    @PostMapping("/withdraw")
    public ResponseEntity<Account> withdraw(@RequestBody Map<String, String> request) {
        Account account = accountService.withdraw(
                request.get("accountNumber"),
                Double.parseDouble(request.get("amount"))
        );
        return ResponseEntity.ok(account);
    }

    // Close account
    @PutMapping("/close/{accountNumber}")
    public ResponseEntity<String> closeAccount(@PathVariable String accountNumber) {
        return ResponseEntity.ok(accountService.closeAccount(accountNumber));
    }

    // Health check
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Account Service is running!");
    }
}