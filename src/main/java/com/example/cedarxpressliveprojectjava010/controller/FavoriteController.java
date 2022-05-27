package com.example.cedarxpressliveprojectjava010.controller;

import com.example.cedarxpressliveprojectjava010.entity.Favorite;
import com.example.cedarxpressliveprojectjava010.entity.Product;
import com.example.cedarxpressliveprojectjava010.entity.User;
import com.example.cedarxpressliveprojectjava010.service.FavoriteService;
import com.example.cedarxpressliveprojectjava010.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/favorites")
@RequiredArgsConstructor
public class FavoriteController {
    private final FavoriteService favoriteService;
    private final ProductService productService;

    @PostMapping("/addToFavorite/{productId}/{userId}")
    public ResponseEntity<String> addProductToFavorite(@PathVariable("userId") long userId, @PathVariable long productId) {
       return favoriteService.addProductToFavorite(productId, userId);
    }

    @DeleteMapping("/deleteFavorite/{productId}/{userId}")
    public ResponseEntity<String> deleteProductFromFavorite
            (@PathVariable(value = "productId") Long productId, @PathVariable(value = "userId") Long userId) {
        return favoriteService.deleteProductFromFavorite(productId, userId);

    }



}
