package com.example.transactionservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "account-service")
public interface AccountClient {

    @PostMapping("/api/accounts/deposit")
    Map<String, Object> deposit(@RequestBody Map<String, String> request);

    @PostMapping("/api/accounts/withdraw")
    Map<String, Object> withdraw(@RequestBody Map<String, String> request);
}