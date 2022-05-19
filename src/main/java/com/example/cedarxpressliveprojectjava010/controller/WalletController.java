package com.example.cedarxpressliveprojectjava010.controller;

import com.example.cedarxpressliveprojectjava010.dto.request.FundWalletRequest;
import com.example.cedarxpressliveprojectjava010.entity.Wallet;
import com.example.cedarxpressliveprojectjava010.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @PostMapping("/fund-wallet")
    ResponseEntity<Wallet> fundWallet(@RequestBody FundWalletRequest fundWalletRequest){
        return walletService.fundWallet(fundWalletRequest);
    }



}
