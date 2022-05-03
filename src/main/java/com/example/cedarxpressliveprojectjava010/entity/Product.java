package com.example.cedarxpressliveprojectjava010.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Product extends Base{


    private String productName;
    private String description;
    private long quantity;
    private double price;

    @OneToMany
    @ToString.Exclude
    private List<ImageUrl> images;

    @ManyToOne
    private SubCategory subCategory;

    @ManyToOne
    private Category category;


}
