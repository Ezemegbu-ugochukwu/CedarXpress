package com.example.cedarxpressliveprojectjava010.service;

import com.example.cedarxpressliveprojectjava010.entity.Cart;
import org.springframework.http.ResponseEntity;

public interface CartItemService {
 ResponseEntity<Cart> addToCart (Long productId);
 ResponseEntity<String> removeFromCart(Long productId, String userEmail);
 ResponseEntity<String> clearCart();
}
