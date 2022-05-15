package com.example.cedarxpressliveprojectjava010.service.impl;
import com.example.cedarxpressliveprojectjava010.dto.ProductDto;
import com.example.cedarxpressliveprojectjava010.entity.Category;
import com.example.cedarxpressliveprojectjava010.entity.ImageUrl;
import com.example.cedarxpressliveprojectjava010.entity.Product;
import com.example.cedarxpressliveprojectjava010.entity.SubCategory;
import com.example.cedarxpressliveprojectjava010.repository.CategoryRepository;
import com.example.cedarxpressliveprojectjava010.repository.ImageUrlRepository;
import com.example.cedarxpressliveprojectjava010.repository.ProductRepository;
import com.example.cedarxpressliveprojectjava010.repository.SubCategoryRepository;
import com.example.cedarxpressliveprojectjava010.service.AdminService;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
@ExtendWith(MockitoExtension.class)
class AdminServiceImplTest {
    @InjectMocks
    private AdminServiceImpl adminService;
    @Mock
    private  CategoryRepository categoryRepository;
    @Mock
    private  SubCategoryRepository subCategoryRepository;
    @Mock
    private  ProductRepository productRepository;
    @Mock
    private  ImageUrlRepository imageUrlRepository;
    ProductDto productDto;
    Product product;
    Category category;
    SubCategory subCategory;
    ImageUrl imgUrl;
    @BeforeEach
    void setUp() {
        adminService = new AdminServiceImpl(categoryRepository,subCategoryRepository,productRepository,imageUrlRepository);
        productDto = ProductDto.builder()
                .productName("reading chair")
                .description("egronomic chair to rest the neck")
                .category("office")
                .subCategory("wooden")
                .price(120000)
                .build();
        category = Category.builder()
                .categoryName("office").build();
        subCategory = SubCategory.builder()
                .subCategoryName("wooden")
                .build();
        product = Product.builder()
                .productName("reading chair")
                .description("egronomic chair to rest the neck")
                .category(category)
                .subCategory(subCategory)
                .price(120000)
                .build();
        product.setId(1L);
        imgUrl = ImageUrl.builder().img("furniture1.jpeg").build();
    }
    @Test
    void createProduct() {
        given(categoryRepository.findCategoryByCategoryName(productDto.getCategory())).willReturn(Optional.of(category));
        given(subCategoryRepository.findSubCategoryBySubCategoryName(productDto.getSubCategory())).willReturn(Optional.of(subCategory));
        given(productRepository.save(any())).willReturn(product);
        ResponseEntity<Product> response = adminService.createProduct(productDto);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(product);
    }


    @Test
    void addProductImage() {
        String img = "furniture1.jpeg";
        List<ImageUrl> images = new ArrayList<>();
        images.add(imgUrl);
        product.setImages(images);
        given(productRepository.findById(1l)).willReturn(Optional.of(product));
        given(imageUrlRepository.save(any())).willReturn(imgUrl);
        ResponseEntity<Product> response = adminService.addProductImage(img, 1l);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(product);
    }
}