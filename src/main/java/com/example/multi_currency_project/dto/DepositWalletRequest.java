package com.example.multi_currency_project.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DepositWalletRequest {
    BigDecimal amount;
}
