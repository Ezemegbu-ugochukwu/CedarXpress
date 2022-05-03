package com.example.cedarxpressliveprojectjava010.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Wallet extends Base{

    @Transient
    private Locale currentLocale = Locale.getDefault();
    @Transient
    private Currency currentCurrency = Currency.getInstance(currentLocale);
    @Transient
    private NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(currentLocale);
    @Transient
    private double balance;


    private String currentBalance  = currencyFormatter.format(balance);


    @OneToOne
    @JsonIgnore
    private User customer;


}
