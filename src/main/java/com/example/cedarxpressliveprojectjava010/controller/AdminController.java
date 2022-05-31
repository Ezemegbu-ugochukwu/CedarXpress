package com.example.cedarxpressliveprojectjava010.controller;

import com.example.cedarxpressliveprojectjava010.dto.ProductDto;
import com.example.cedarxpressliveprojectjava010.entity.Product;
import com.example.cedarxpressliveprojectjava010.service.AdminService;
import com.example.cedarxpressliveprojectjava010.service.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "/api/admin/")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private CloudinaryService cloudinaryService;

    @RequestMapping(value = "createProduct", method = RequestMethod.POST)
    public ResponseEntity<Product> createProduct(@RequestBody ProductDto product) {
        return adminService.createProduct(product);
    }
    @RequestMapping(value = "uploadImage", method = RequestMethod.POST)
    public ResponseEntity<Product> uploadImage(@RequestPart MultipartFile image, @RequestParam("id") Product product) {
        String url = cloudinaryService.uploadFile(image);
        Long productId = product.getId();
        return adminService.addProductImage(url,productId);
    }

    @DeleteMapping(value="/Products/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Long id) {
        return adminService.deleteProduct(id);
    }

    @PutMapping("/updateProduct{id}")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto, @PathVariable(name = "id") Long id) {
        ProductDto newProduct = adminService.updateProduct(productDto, id);
        return ResponseEntity.ok(newProduct);
    }
}