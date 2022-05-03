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
public class AddressList extends Base{

    @OneToMany(mappedBy = "addressList")
    @ToString.Exclude
    private List<Address> addressList = new ArrayList<>();

    @OneToOne
    private User customer;
}
