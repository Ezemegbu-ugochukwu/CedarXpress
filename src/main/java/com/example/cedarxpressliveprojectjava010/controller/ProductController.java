package com.example.cedarxpressliveprojectjava010.controller;

import com.example.cedarxpressliveprojectjava010.dto.ProductDto;
import com.example.cedarxpressliveprojectjava010.service.ProductService;
import com.example.cedarxpressliveprojectjava010.util.AppConstants;
import com.example.cedarxpressliveprojectjava010.util.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<ProductResponse> getAllProducts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false) int pageSize
            ){
        return productService.getAllProducts(pageNo, pageSize);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getASingleProduct(@PathVariable("id") long id){
        return productService.getASingleProduct(id);

    }
}
