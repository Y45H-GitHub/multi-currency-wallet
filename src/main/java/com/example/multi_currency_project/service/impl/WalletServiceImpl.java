package com.example.multi_currency_project.service.impl;

import com.example.multi_currency_project.dto.CreateWalletResponse;
import com.example.multi_currency_project.dto.WalletBalanceResponse;
import com.example.multi_currency_project.entity.CurrencyEnum;
import com.example.multi_currency_project.entity.Wallet;
import com.example.multi_currency_project.exception.InvalidDepositAmountException;
import com.example.multi_currency_project.exception.OptimisticLockingException;
import com.example.multi_currency_project.exception.WalletNotFoundException;
import com.example.multi_currency_project.repo.WalletRepo;
import com.example.multi_currency_project.service.WalletService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class WalletServiceImpl implements WalletService {

    private final WalletRepo walletRepo;

    @Override
    public CreateWalletResponse createWallet(UUID userId) {
        Optional<Wallet> existingWallet = walletRepo.findByUserId(userId);
        
        if (existingWallet.isPresent()) {
            Wallet wallet = existingWallet.get();
            return new CreateWalletResponse(wallet.getWalletId(), wallet.getBalance(), wallet.getCurrency());
        }

        Wallet wallet = new Wallet();
        wallet.setUserId(userId);
        wallet.setCurrency(CurrencyEnum.NGN);
        wallet.setBalance(BigDecimal.ZERO);
        wallet = walletRepo.save(wallet);

        return new CreateWalletResponse(wallet.getWalletId(), wallet.getBalance(), wallet.getCurrency());
    }

    @Override
    public void deposit(UUID walletId, BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidDepositAmountException("Deposit amount must be positive");
        }

        Wallet wallet = walletRepo.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException("Wallet not found with ID: " + walletId));

        BigDecimal newBalance = wallet.getBalance().add(amount);
        wallet.setBalance(newBalance);
        
        try {
            walletRepo.save(wallet);
        } catch (OptimisticLockingFailureException ex) {
            throw new OptimisticLockingException("Wallet was modified during deposit. Please retry.");
        }
    }

    @Override
    public WalletBalanceResponse balance(UUID walletId) {
        Wallet wallet = walletRepo.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException("Wallet not found with ID: " + walletId));
        
        WalletBalanceResponse response = new WalletBalanceResponse();
        response.setBalance(wallet.getBalance());
        return response;
    }
}
