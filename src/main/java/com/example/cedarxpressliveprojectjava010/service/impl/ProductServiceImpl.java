package com.example.cedarxpressliveprojectjava010.service.impl;


import com.example.cedarxpressliveprojectjava010.dto.ProductDto;
import com.example.cedarxpressliveprojectjava010.entity.Product;
import com.example.cedarxpressliveprojectjava010.repository.ProductRepository;
import com.example.cedarxpressliveprojectjava010.service.ProductService;
import com.example.cedarxpressliveprojectjava010.util.ProductResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper mapper;

    @Override
    public ResponseEntity<ProductResponse> getAllProducts(int pageNo, int pageSize) {

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Product> productPage = productRepository.findAll(pageable);
        List<Product> productList = productPage.getContent();
        List<ProductDto> content = productList.stream().map(this::mapToDto).collect(Collectors.toList());

        ProductResponse productResponse = ProductResponse.builder()
                .content(content)
                .pageNo(productPage.getNumber())
                .pageSize(productPage.getSize())
                .totalElements(productPage.getTotalElements())
                .totalPages(productPage.getTotalPages())
                .last(productPage.isLast())
                .build();

        return ResponseEntity.ok(productResponse);

    }

    @Override
    public ResponseEntity<ProductDto> getASingleProduct(long id) {
        Product product = productRepository.findById(id).orElseThrow(RuntimeException::new);
        return new ResponseEntity<>(mapToDto(product), HttpStatus.OK);
    }

    public ProductDto mapToDto(Product product){
        return mapper.map(product, ProductDto.class);
    }
}
