package com.example.cedarxpressliveprojectjava010.service.impl;
import com.example.cedarxpressliveprojectjava010.dto.ProductDto;
import com.example.cedarxpressliveprojectjava010.entity.Category;
import com.example.cedarxpressliveprojectjava010.entity.ImageUrl;
import com.example.cedarxpressliveprojectjava010.entity.Product;
import com.example.cedarxpressliveprojectjava010.entity.SubCategory;
import com.example.cedarxpressliveprojectjava010.exception.ProductNotFoundException;
import com.example.cedarxpressliveprojectjava010.repository.CategoryRepository;
import com.example.cedarxpressliveprojectjava010.repository.ImageUrlRepository;
import com.example.cedarxpressliveprojectjava010.repository.ProductRepository;
import com.example.cedarxpressliveprojectjava010.repository.SubCategoryRepository;
import com.example.cedarxpressliveprojectjava010.service.AdminService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final ProductRepository productRepository;
    private final ImageUrlRepository imageUrlRepository;

    @Override
    @Transactional
    public ResponseEntity<Product> createProduct(ProductDto productDto) {
        Product newProduct = new Product();
        Optional<Category> productCategory = categoryRepository.findCategoryByCategoryName(productDto.getCategory());
        Category category;
        if (productCategory.isEmpty()) {
            category = Category.builder().categoryName(productDto.getCategory()).build();
            categoryRepository.save(category);
            newProduct.setCategory(category);
        } else {
            category = productCategory.get();
            newProduct.setCategory(category);
        }
        Optional<SubCategory>  productSubCategory = subCategoryRepository.findSubCategoryBySubCategoryName(productDto.getSubCategory());
        if (productSubCategory.isEmpty()){
            SubCategory subCategory = SubCategory.builder().subCategoryName(productDto.getSubCategory().toLowerCase()).build();
            subCategory.setCategory(category);
            subCategoryRepository.save(subCategory);
            newProduct.setSubCategory(subCategory);
        } else {
            newProduct.setSubCategory(productSubCategory.get());
        }
        newProduct.setProductName(productDto.getProductName().toLowerCase());
        newProduct.setDescription(productDto.getDescription().toLowerCase());
        newProduct.setPrice(productDto.getPrice());
        newProduct = productRepository.save(newProduct);
        return new ResponseEntity<Product>(newProduct, HttpStatus.CREATED);
    }
    @Override
    @Transactional
    public ResponseEntity<Product> addProductImage(String img, Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(()-> new ProductNotFoundException("product with ID "+productId+" Does not Exist"));
        ImageUrl imgUrl = ImageUrl.builder().img(img).build();
        imgUrl = imageUrlRepository.save(imgUrl);
        product.getImages().add(imgUrl);
        productRepository.save(product);
        return ResponseEntity.ok(product);
    }
}