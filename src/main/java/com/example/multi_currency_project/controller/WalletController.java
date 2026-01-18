package com.example.multi_currency_project.controller;

import com.example.multi_currency_project.dto.*;
import com.example.multi_currency_project.service.WalletService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wallets")
@Validated
public class WalletController {
    private final WalletService walletService;

    @PostMapping
    public ResponseEntity<CreateWalletResponse> createWallet(@Valid @RequestBody CreateWalletRequest request){
        CreateWalletResponse response = walletService.createWallet(request.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{walletId}/balance")
    public ResponseEntity<WalletBalanceResponse> checkBalance(@PathVariable UUID walletId) {
        WalletBalanceResponse response = walletService.balance(walletId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{walletId}/deposit")
    public ResponseEntity<Void> deposit(@PathVariable UUID walletId, @Valid @RequestBody DepositWalletRequest request) {
        walletService.deposit(walletId, request.getAmount());
        return ResponseEntity.ok().build();
    }
}
