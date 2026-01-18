package com.example.multi_currency_project.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class WalletBalanceRequest {
    @NotNull(message = "Wallet ID is required")
    private UUID walletId;
}
