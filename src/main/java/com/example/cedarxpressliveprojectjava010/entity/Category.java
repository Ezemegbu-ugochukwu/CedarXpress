package com.example.cedarxpressliveprojectjava010.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Category extends Base{


    private String categoryName;
    private String description;

    @OneToMany
    @ToString.Exclude
    private List<SubCategory> subCategories = new ArrayList<>();

}
