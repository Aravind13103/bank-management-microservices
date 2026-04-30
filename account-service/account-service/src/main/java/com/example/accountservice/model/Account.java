package com.example.accountservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String accountNumber;

    @Column(nullable = false)
    private String accountType; // SAVINGS or CURRENT

    @Column(nullable = false)
    private Double balance;

    @Column(nullable = false)
    private Long userId; // links to User Service

    @Column(nullable = false)
    private String status; // ACTIVE or INACTIVE
}