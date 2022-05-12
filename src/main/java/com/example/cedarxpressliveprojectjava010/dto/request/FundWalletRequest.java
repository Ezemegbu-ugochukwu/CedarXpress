package com.example.cedarxpressliveprojectjava010.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FundWalletRequest {

    private String email;
    private double amount;
}
