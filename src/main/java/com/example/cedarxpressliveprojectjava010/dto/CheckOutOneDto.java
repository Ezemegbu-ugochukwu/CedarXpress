package com.example.cedarxpressliveprojectjava010.dto;

import com.example.cedarxpressliveprojectjava010.entity.*;
import com.example.cedarxpressliveprojectjava010.enums.Payment;
import lombok.*;
import org.springframework.data.annotation.Transient;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CheckOutOneDto {
    private Cart cart;
    private String state;
    private String city;
    private String address;
    private Integer zipCode;
    private String phoneNumber;
    private String payment;

    @Transient
    public Payment getPaymentMethod(){
        if(payment.equals("CARD")){
            return Payment.CARD;
        }else if(payment.equals("DELIVERY")){
            return Payment.PAY_ON_DELIVERY;
        }else return Payment.WALLET;
    }
}


