package com.example.accountservice.service;

import com.example.accountservice.model.Account;
import com.example.accountservice.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    // Create new account
    public Account createAccount(Long userId, String accountType) {
        String accountNumber = generateAccountNumber();

        Account account = new Account();
        account.setUserId(userId);
        account.setAccountNumber(accountNumber);
        account.setAccountType(accountType != null ? accountType : "SAVINGS");
        account.setBalance(0.0);
        account.setStatus("ACTIVE");

        return accountRepository.save(account);
    }

    // Get account by account number
    public Account getAccount(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found!"));
    }

    // Get all accounts of a user
    public List<Account> getAccountsByUserId(Long userId) {
        return accountRepository.findByUserId(userId);
    }

    // Deposit money
    public Account deposit(String accountNumber, Double amount) {
        Account account = getAccount(accountNumber);
        account.setBalance(account.getBalance() + amount);
        return accountRepository.save(account);
    }

    // Withdraw money
    public Account withdraw(String accountNumber, Double amount) {
        Account account = getAccount(accountNumber);
        if (account.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance!");
        }
        account.setBalance(account.getBalance() - amount);
        return accountRepository.save(account);
    }

    // Deactivate account
    public String closeAccount(String accountNumber) {
        Account account = getAccount(accountNumber);
        account.setStatus("INACTIVE");
        accountRepository.save(account);
        return "Account closed successfully!";
    }

    // Auto generate account number
    private String generateAccountNumber() {
        long count = accountRepository.count() + 1;
        return "ACC" + String.format("%04d", count);
    }
}