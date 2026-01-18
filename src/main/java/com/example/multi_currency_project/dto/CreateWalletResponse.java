package com.example.multi_currency_project.dto;

import com.example.multi_currency_project.entity.CurrencyEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class CreateWalletResponse {

    private UUID walletId;
    private BigDecimal balance;
    private CurrencyEnum currencyEnum;

    public CreateWalletResponse(UUID walletId, BigDecimal balance, CurrencyEnum currencyEnum) {
        this.walletId = walletId;
        this.balance = balance;
        this.currencyEnum = currencyEnum;
    }
}
