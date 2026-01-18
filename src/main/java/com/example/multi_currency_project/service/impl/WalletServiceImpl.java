package com.example.multi_currency_project.service.impl;

import com.example.multi_currency_project.dto.CreateWalletResponse;
import com.example.multi_currency_project.dto.WalletBalanceResponse;
import com.example.multi_currency_project.entity.CurrencyEnum;
import com.example.multi_currency_project.entity.Wallet;
import com.example.multi_currency_project.repo.WalletRepo;
import com.example.multi_currency_project.service.WalletService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepo walletRepo;

    @Override
    @Transactional
    public CreateWalletResponse createWallet(UUID userId) {

        Optional<Wallet> existingWallet = walletRepo.findByUserId(userId);
        Wallet wallet;

        if(existingWallet.isPresent()){
            wallet = existingWallet.get();
        }else{
            wallet = new Wallet();
            wallet.setUserId(userId);
            wallet.setCurrency(CurrencyEnum.NGN);
            wallet.setBalance(BigDecimal.ZERO);
            wallet = walletRepo.save(wallet);
        }

        return new CreateWalletResponse(wallet.getWalletId(), wallet.getBalance(), wallet.getCurrency());
    }

    @Override
    @Transactional
    public void deposit(UUID walletId, BigDecimal amount) {
//         find the wallet by id if there
        if(amount==null||amount.compareTo(BigDecimal.ZERO)<=0){
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        Wallet wallet = walletRepo.findById(walletId).orElseThrow(()->new IllegalArgumentException("Wallet not found"));

        BigDecimal newBalance = wallet.getBalance().add(amount);
        wallet.setBalance(newBalance);
    }

    @Override
    public WalletBalanceResponse balance(UUID walletId) {
        Wallet wallet = walletRepo.findById(walletId).orElseThrow(()->new IllegalArgumentException("Wallet not found"));
        WalletBalanceResponse response = new WalletBalanceResponse();
        response.setBalance(wallet.getBalance());
        return response;
    }
}
