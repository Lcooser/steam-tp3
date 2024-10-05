package com.steamDemo.api.controller;

import com.steamDemo.api.domain.Account.Account;
import com.steamDemo.api.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestParam UUID userId, @RequestParam double initialBalance) {
        Account account = accountService.createAccount(userId, initialBalance);
        return new ResponseEntity<>(account, HttpStatus.CREATED);
    }

    @PutMapping("/{accountId}")
    public ResponseEntity<Account> updateAccount(@PathVariable UUID accountId, @RequestParam double newBalance) {
        Account account = accountService.updateAccount(accountId, newBalance);
        return ResponseEntity.ok(account);
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable UUID accountId) {
        accountService.deleteAccount(accountId);
        return ResponseEntity.noContent().build();
    }
}
