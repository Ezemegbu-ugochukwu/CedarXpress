package com.example.cedarxpressliveprojectjava010.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Favorite extends Base{

    @OneToMany
    @ToString.Exclude
    private Set<Product> favorites = new HashSet<>();

    @OneToOne
    private User customer;
}
