package com.example.cedarxpressliveprojectjava010.service.implementation;

import com.example.cedarxpressliveprojectjava010.dto.request.FundWalletRequest;
import com.example.cedarxpressliveprojectjava010.entity.User;
import com.example.cedarxpressliveprojectjava010.entity.Wallet;
import com.example.cedarxpressliveprojectjava010.entity.WalletTransaction;
import com.example.cedarxpressliveprojectjava010.enums.Payment;
import com.example.cedarxpressliveprojectjava010.exception.NotFoundException;
import com.example.cedarxpressliveprojectjava010.repository.UserRepository;
import com.example.cedarxpressliveprojectjava010.repository.WalletRepository;
import com.example.cedarxpressliveprojectjava010.repository.WalletTransactionsRepository;
import com.example.cedarxpressliveprojectjava010.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final WalletTransactionsRepository walletTransactionsRepository;

    @Override
    public ResponseEntity<Wallet> fundWallet(FundWalletRequest fundWalletRequest) {
        Optional<User> user = userRepository.findUserByEmail(fundWalletRequest.getEmail());
        if (user.isEmpty()) {
            throw new NotFoundException("Kindly input correct email");
        }
        Optional<Wallet> wallet = walletRepository.findWalletByUserEmail(fundWalletRequest.getEmail());
        Wallet wallet2;
        if (wallet.isPresent()) {
            double balance = wallet.get().getBalance();
            wallet.get().setBalance(balance + fundWalletRequest.getAmount());
            WalletTransaction walletTransaction = new WalletTransaction();
            walletTransaction.setTransactionType(Payment.WALLET);
            walletTransaction.setAmount(fundWalletRequest.getAmount());
            walletTransaction.setModifiedTime(LocalDateTime.now());
            walletTransaction.setCreatedTime(LocalDateTime.now());
            walletTransaction.setWallet(wallet.get());
            Wallet wallet1 = walletRepository.save(wallet.get());
            walletTransactionsRepository.save(walletTransaction);
            return new ResponseEntity<>(wallet1, HttpStatus.OK);
        } else {
            Wallet wallet1 = new Wallet();
            wallet1.setUser(user.get());
            wallet1.setBalance(fundWalletRequest.getAmount());
            WalletTransaction walletTransaction = new WalletTransaction();
            walletTransaction.setTransactionType(Payment.WALLET);
            walletTransaction.setAmount(fundWalletRequest.getAmount());
            walletTransaction.setModifiedTime(LocalDateTime.now());
            walletTransaction.setCreatedTime(LocalDateTime.now());
            walletTransaction.setWallet(wallet1);
            wallet2 = walletRepository.save(wallet1);
            walletTransactionsRepository.save(walletTransaction);
        }

        return new ResponseEntity<>(wallet2, HttpStatus.OK );
    }
}



