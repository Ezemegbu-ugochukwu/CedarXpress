package com.example.cedarxpressliveprojectjava010.service;

import com.example.cedarxpressliveprojectjava010.dto.ProductDto;
import com.example.cedarxpressliveprojectjava010.util.ProductResponse;
import org.springframework.http.ResponseEntity;

public interface ProductService {
    ResponseEntity<ProductResponse> getAllProducts(int pageNo, int pageSize);
    ResponseEntity<ProductDto> getASingleProduct(long id);
}
