package com.example.cedarxpressliveprojectjava010.service.impl;

import com.example.cedarxpressliveprojectjava010.dto.ProductDto;
import com.example.cedarxpressliveprojectjava010.entity.Category;
import com.example.cedarxpressliveprojectjava010.entity.Product;
import com.example.cedarxpressliveprojectjava010.entity.SubCategory;
import com.example.cedarxpressliveprojectjava010.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ModelMapper mapper;
    @InjectMocks
    private ProductServiceImpl underTest;

    private Product product;
    private ProductDto productDto;
    private SubCategory subCategory;

    @BeforeEach
    void setUp(){
        List<SubCategory> subCategories = new ArrayList<>();
        subCategories.add(subCategory);

        Category category = Category.builder()
                .categoryName("")
                .description("")
                .subCategories(subCategories)
                .build();

        subCategory = SubCategory.builder()
                .subCategoryName("")
                .category(category)
                .build();

        productDto = ProductDto.builder()
                .productName("dining_set")
                .description("best_in_town")
                .quantity(3)
                .price(400.00)
                .subCategory(subCategory)
                .category(category)
                .build();

        product = new Product();
        product.setId(9L);
        product.setProductName(productDto.getProductName());
    }


    @Test
    void getAllProducts() {
        Pageable pageable = PageRequest.of(0,2);
        List<Product> productList = List.of(product);
        Page<Product> productPage = new PageImpl<>(productList);

       when(productRepository.findAll(pageable)).thenReturn(productPage);

        underTest.getAllProducts(0,2);
        verify(productRepository).findAll(pageable);



}

    @Test
    void getASingleProduct() {
        when(productRepository.findById(product.getId())).thenReturn(Optional.ofNullable(product));
        when(mapper.map(product, ProductDto.class)).thenReturn(productDto);

        ResponseEntity<ProductDto> response = underTest.getASingleProduct(product.getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isEqualTo(productDto);
    }
}
