package com.example.multi_currency_project.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateWalletRequest {
    @NotNull(message = "User ID is required")
    private UUID userId;
}
