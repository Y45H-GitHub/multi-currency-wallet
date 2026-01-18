package com.example.multi_currency_project.service;

import com.example.multi_currency_project.dto.CreateWalletResponse;
import com.example.multi_currency_project.dto.WalletBalanceResponse;

import java.math.BigDecimal;
import java.util.UUID;

public interface WalletService {

    CreateWalletResponse createWallet(UUID userId);

    void deposit(UUID walletId, BigDecimal amount);

    WalletBalanceResponse balance(UUID walletId);
}
