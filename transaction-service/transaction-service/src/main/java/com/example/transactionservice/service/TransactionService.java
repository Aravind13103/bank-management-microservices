package com.example.transactionservice.service;

import com.example.transactionservice.client.AccountClient;
import com.example.transactionservice.model.Transaction;
import com.example.transactionservice.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountClient accountClient;

    public TransactionService(TransactionRepository transactionRepository, AccountClient accountClient) {
        this.transactionRepository = transactionRepository;
        this.accountClient = accountClient;
    }

    // Deposit
    public Transaction deposit(String accountNumber, Double amount, String description) {
        Transaction transaction = new Transaction();
        transaction.setTransactionType("DEPOSIT");
        transaction.setFromAccount(accountNumber);
        transaction.setAmount(amount);
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setDescription(description);

        try {
            Map<String, String> request = new HashMap<>();
            request.put("accountNumber", accountNumber);
            request.put("amount", String.valueOf(amount));
            accountClient.deposit(request);
            transaction.setStatus("SUCCESS");
        } catch (Exception e) {
            transaction.setStatus("FAILED");
        }

        return transactionRepository.save(transaction);
    }

    // Withdrawal
    public Transaction withdraw(String accountNumber, Double amount, String description) {
        Transaction transaction = new Transaction();
        transaction.setTransactionType("WITHDRAWAL");
        transaction.setFromAccount(accountNumber);
        transaction.setAmount(amount);
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setDescription(description);

        try {
            Map<String, String> request = new HashMap<>();
            request.put("accountNumber", accountNumber);
            request.put("amount", String.valueOf(amount));
            accountClient.withdraw(request);
            transaction.setStatus("SUCCESS");
        } catch (Exception e) {
            transaction.setStatus("FAILED");
        }

        return transactionRepository.save(transaction);
    }

    // Transfer
    public Transaction transfer(String fromAccount, String toAccount, Double amount, String description) {
        Transaction transaction = new Transaction();
        transaction.setTransactionType("TRANSFER");
        transaction.setFromAccount(fromAccount);
        transaction.setToAccount(toAccount);
        transaction.setAmount(amount);
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setDescription(description);

        try {
            // Withdraw from sender
            Map<String, String> withdrawRequest = new HashMap<>();
            withdrawRequest.put("accountNumber", fromAccount);
            withdrawRequest.put("amount", String.valueOf(amount));
            accountClient.withdraw(withdrawRequest);

            // Deposit to receiver
            Map<String, String> depositRequest = new HashMap<>();
            depositRequest.put("accountNumber", toAccount);
            depositRequest.put("amount", String.valueOf(amount));
            accountClient.deposit(depositRequest);

            transaction.setStatus("SUCCESS");
        } catch (Exception e) {
            transaction.setStatus("FAILED");
        }

        return transactionRepository.save(transaction);
    }

    // Get transaction history of an account
    public List<Transaction> getHistory(String accountNumber) {
        return transactionRepository.findByFromAccountOrToAccount(accountNumber, accountNumber);
    }

    // Get all transactions
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
}