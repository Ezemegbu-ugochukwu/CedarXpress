package com.example.cedarxpressliveprojectjava010.entity;

import com.example.cedarxpressliveprojectjava010.enums.Payment;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Order extends Base{

    @OneToMany (mappedBy = "order")
    @ToString.Exclude
    private List<OrderItem> customerOrder = new ArrayList<>();

    private LocalDateTime timeOfCompletion;
    private Boolean isArrived;
    private Boolean isActive;


    private double deliveryFee;
    private double discount;
    private double price;

    @Enumerated(EnumType.STRING)
    private Payment paymentMethod;

    @OneToOne
    private Address address;

    @ManyToOne
    @JsonIgnore
    private User customer;


    @ManyToOne
    @JsonIgnore
    private User rider;

}

