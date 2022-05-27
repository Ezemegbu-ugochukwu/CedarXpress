package com.example.cedarxpressliveprojectjava010.service;

import com.example.cedarxpressliveprojectjava010.dto.ProductDto;
import com.example.cedarxpressliveprojectjava010.entity.Product;
import com.example.cedarxpressliveprojectjava010.entity.User;
import org.springframework.http.ResponseEntity;

public interface FavoriteService {
    ResponseEntity<String> addProductToFavorite(Long productId, Long userId);
    ResponseEntity<String>deleteProductFromFavorite(Long productId, Long userId);

}
