package com.example.cedarxpressliveprojectjava010.service;

import com.example.cedarxpressliveprojectjava010.dto.ViewProductDto;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface ProductService {
    List<ViewProductDto> fetchAllProducts(int pageNo, int pageSize, String sortBy, String keyword);
    ResponseEntity<ViewProductDto> getASingleProduct(long id);
}
