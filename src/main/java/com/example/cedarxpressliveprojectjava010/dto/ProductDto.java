package com.example.cedarxpressliveprojectjava010.dto;
import com.example.cedarxpressliveprojectjava010.entity.Category;
import com.example.cedarxpressliveprojectjava010.entity.SubCategory;
import lombok.*;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

        private String productName;
        private String description;
        private double price;
        private SubCategory subCategory;
        private Category category;
    }

