package com.example.cedarxpressliveprojectjava010.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Cart extends Base{


    @OneToMany(mappedBy = "cart")
    @ToString.Exclude
    private List<CartItem> cartItems = new ArrayList<>();

    @OneToOne
    @JsonIgnore
    private User customer;
}
