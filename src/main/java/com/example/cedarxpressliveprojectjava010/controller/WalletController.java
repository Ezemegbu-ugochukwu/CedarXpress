package com.example.cedarxpressliveprojectjava010.controller;

import com.example.cedarxpressliveprojectjava010.dto.WalletDto;
import com.example.cedarxpressliveprojectjava010.dto.request.FundWalletRequest;
import com.example.cedarxpressliveprojectjava010.entity.Wallet;
import com.example.cedarxpressliveprojectjava010.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
public class WalletController {

    @Autowired
    private WalletService walletService;

    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @PostMapping("/fund-wallet")
    ResponseEntity<Wallet> fundWallet(@RequestBody FundWalletRequest fundWalletRequest){
        return walletService.fundWallet(fundWalletRequest);
    }

    @GetMapping("/{userId}/wallet")
    public ResponseEntity<WalletDto> checkWalletBalance(@PathVariable("userId") Long userId){
        return walletService.checkBalance(userId);
    }

}
