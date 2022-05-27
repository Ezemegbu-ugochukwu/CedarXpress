package com.example.cedarxpressliveprojectjava010.entity;

import lombok.*;

import javax.persistence.*;
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

    @ManyToOne(cascade = CascadeType.ALL)
    private Product product;

    @ManyToOne
    private User user;
}
