package com.example.cedarxpressliveprojectjava010.service.impl;

import com.example.cedarxpressliveprojectjava010.entity.Cart;
import com.example.cedarxpressliveprojectjava010.entity.CartItem;
import com.example.cedarxpressliveprojectjava010.entity.Product;
import com.example.cedarxpressliveprojectjava010.entity.User;
import com.example.cedarxpressliveprojectjava010.exception.CartNotFoundException;
import com.example.cedarxpressliveprojectjava010.exception.ProductNotFoundException;
import com.example.cedarxpressliveprojectjava010.exception.UserNotFoundException;
import com.example.cedarxpressliveprojectjava010.repository.CartItemRepository;
import com.example.cedarxpressliveprojectjava010.repository.CartRepository;
import com.example.cedarxpressliveprojectjava010.repository.ProductRepository;
import com.example.cedarxpressliveprojectjava010.repository.UserRepository;
import com.example.cedarxpressliveprojectjava010.service.CartItemService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class CartItemServiceImpl implements CartItemService {

    private ProductRepository productRepository;
    private UserRepository userRepository;
    private CartRepository cartRepository;
    private CartItemRepository cartItemRepository;


    @Override
    public ResponseEntity<Cart> addToCart(Long productId) {
        String loggedInEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("product does not exist"));
        User user = Optional.ofNullable(userRepository.getUserByEmail(loggedInEmail)).orElseThrow(() -> new UserNotFoundException("User not found"));
        Optional<Cart> optionalCart = cartRepository.findCartByCustomer(user);
        Cart cart;
        if (optionalCart.isEmpty()) {
            CartItem cartItem = CartItem.builder()
                    .product(product)
                    .unit(1)
                    .build();

            cart = Cart.builder()
                    .customer(user)
                    .cartItems(List.of(cartItem))
                    .build();
            cartRepository.save(cart);

        } else {
            cart = optionalCart.get();
            Optional<CartItem> optionalCartItem = cartItemRepository.findCartItemByCartAndProduct(cart, product);
            if (optionalCartItem.isEmpty()) {
                CartItem newCartItem = new CartItem();
                newCartItem.setCart(cart);
                newCartItem.setProduct(product);
                newCartItem.setUnit(1);
//          cartItemRepository.save(newCartItem);
                List<CartItem> cartItems = new ArrayList<>();
                cartItems.add(newCartItem);
                cart.setCartItems(cartItems);
                cartRepository.save(cart);
            } else {
                List<CartItem> cartItems = cart.getCartItems();
                cartItems.stream().filter(a -> a.getProduct().getId().equals(productId)).forEachOrdered(a -> a.setUnit(a.getUnit() + 1));
                cart.setCartItems(cartItems);
                cartRepository.save(cart);
            }

        }
        return ResponseEntity.ok(cart);
    }


    @Override
    public ResponseEntity<String> removeFromCart(Long productId, String userEmail) {
        User user = userRepository.findUserByEmail(userEmail).orElseThrow(() -> new UserNotFoundException("User not found"));
        Cart cart = cartRepository.findCartByCustomer(user).orElseThrow(() -> new CartNotFoundException("Cart not found")); //create an exception(cart not found)
        cartItemRepository.deleteCartItemByCartAndProduct(cart.getId(), productId);
        return new ResponseEntity<String>("product successfully deleted", HttpStatus.OK);
    }


}




