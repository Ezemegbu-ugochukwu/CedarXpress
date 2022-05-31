package com.example.cedarxpressliveprojectjava010.controller;

import com.example.cedarxpressliveprojectjava010.dto.request.AlterProductQuantityRequest;
import com.example.cedarxpressliveprojectjava010.dto.response.CartItemDto;
import com.example.cedarxpressliveprojectjava010.entity.Cart;
import com.example.cedarxpressliveprojectjava010.response.ApiResponse;
import com.example.cedarxpressliveprojectjava010.service.CartItemService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
@AllArgsConstructor
public class CartController {

    private CartItemService cartItemService;

    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @PostMapping("/add/{productId}")
    public ResponseEntity<Cart> addToCart(@PathVariable Long productId) {
        return cartItemService.addToCart(productId);
    }


    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @DeleteMapping ("/remove/{productId}")
    public ResponseEntity<?> removeFromCart(@PathVariable Long productId) {
        String loggedInEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        cartItemService.removeFromCart(productId,loggedInEmail);
        return new ResponseEntity<>(new ApiResponse<>("product was successfully deleted"), HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @DeleteMapping ("/clearCart")
    public ResponseEntity<?> clearCart() {
        return cartItemService.clearCart();
    }

    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    @PutMapping("/alter")
    public ResponseEntity<CartItemDto> alterProductQuantityInCart(@RequestBody AlterProductQuantityRequest request) {
        return cartItemService.alterProductQuantity(request);
    }


}