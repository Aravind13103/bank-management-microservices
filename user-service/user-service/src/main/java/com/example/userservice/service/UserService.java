package com.example.userservice.service;

import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    // Register new user
    public String register(String username, String password, String email, String role) {
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Username already taken!");
        }
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already registered!");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password); // plain text for now
        user.setEmail(email);
        user.setRole(role != null ? role : "ROLE_USER");

        userRepository.save(user);
        return "User registered successfully!";
    }

    // Login and return JWT
    public String login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password!");
        }

        return jwtUtil.generateToken(username);
    }

    // Get user by username
    public User getUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found!"));
    }
}