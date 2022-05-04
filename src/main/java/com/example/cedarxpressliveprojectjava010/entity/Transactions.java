package com.example.cedarxpressliveprojectjava010.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Transactions extends Base{

    @OneToMany
    @ToString.Exclude
    private List<Order> myTransactions = new ArrayList<>();

    @OneToOne
    private User customer;
}
