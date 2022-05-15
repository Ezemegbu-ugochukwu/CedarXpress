package com.example.cedarxpressliveprojectjava010.dto;

import com.example.cedarxpressliveprojectjava010.entity.Address;
import com.example.cedarxpressliveprojectjava010.entity.Cart;
import com.example.cedarxpressliveprojectjava010.enums.Payment;
import lombok.*;
import org.springframework.data.annotation.Transient;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CheckOutTwoDto {
    private Cart cart;
    private Address address;
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
