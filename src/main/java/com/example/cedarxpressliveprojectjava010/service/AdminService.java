package com.example.cedarxpressliveprojectjava010.service;
import com.example.cedarxpressliveprojectjava010.dto.ProductDto;
import com.example.cedarxpressliveprojectjava010.entity.Product;
import org.springframework.http.ResponseEntity;
import java.util.List;
public interface AdminService {
    ResponseEntity<Product> createProduct(ProductDto productDto);
    ResponseEntity<Product> addProductImage(String img, Long productId);
}