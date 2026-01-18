package com.example.multi_currency_project.controller;

import com.example.multi_currency_project.dto.*;
import com.example.multi_currency_project.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wallets")
public class WalletController {
    private final WalletService walletService;

    @PostMapping
    public ResponseEntity<CreateWalletResponse> createWallet(@RequestBody CreateWalletRequest request){
        CreateWalletResponse response = walletService.createWallet(request.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{walletId}/balance")
    public ResponseEntity<WalletBalanceResponse> checkBalance(@PathVariable UUID walletId) {
        WalletBalanceResponse response = walletService.balance(walletId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{walletId}/deposit")
    public ResponseEntity<?> deposit(@RequestParam UUID walletId , @RequestBody DepositWalletRequest request) {
        walletService.deposit(walletId, request.getAmount());
        return ResponseEntity.ok().build();
    }
}
