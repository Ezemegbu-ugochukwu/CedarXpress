package com.example.cedarxpressliveprojectjava010.controller;

import com.example.cedarxpressliveprojectjava010.dto.LoginDTO;
import com.example.cedarxpressliveprojectjava010.entity.Cart;
import com.example.cedarxpressliveprojectjava010.entity.CartItem;
import com.example.cedarxpressliveprojectjava010.entity.Product;
import com.example.cedarxpressliveprojectjava010.entity.User;
import com.example.cedarxpressliveprojectjava010.enums.Gender;
import com.example.cedarxpressliveprojectjava010.service.CartItemService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {CartController.class})
@ExtendWith(SpringExtension.class)
class CartControllerTest {
    @Autowired
    private CartController cartController;
    @MockBean
    private CartItemService cartItemService;
    @MockBean
    private Authentication auth;
    @MockBean
    SecurityContext securityContext;

    @BeforeEach
    void setUp() {
         auth = mock(Authentication.class);
        securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void addToCart() throws Exception {
        User user = User.builder()
                .firstName("ugo")
                .lastName("eze")
                .email("ugo@gmail.com")
                .password("1234")
                .gender(Gender.MALE)
                .build();

        Product product = Product.builder()
                .productName("shoe")
                .description("a black shoe")
                .price(50.00)
                .quantity(2L)
                .build();

        Cart cart = Cart.builder()
                .cartItems(new ArrayList<CartItem>())
                .customer(user)
                .build();

        CartItem cartItem = com.example.cedarxpressliveprojectjava010.entity.CartItem.builder().build();

        given(cartItemService.addToCart((anyLong()))).willReturn(ResponseEntity.ok(cart));
        String content = (new ObjectMapper()).writeValueAsString(product);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/add/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.cartController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void removeFromCart() throws Exception {
        User user = User.builder()
                .firstName("ugo")
                .lastName("eze")
                .email("ugo@gmail.com")
                .password("1234")
                .gender(Gender.MALE)
                .build();

        Product product = Product.builder()
                .productName("shoe")
                .description("a black shoe")
                .price(50.00)
                .quantity(2L)
                .build();

        Cart cart = Cart.builder()
                .cartItems(new ArrayList<CartItem>())
                .customer(user)
                .build();

        CartItem cartItem = com.example.cedarxpressliveprojectjava010.entity.CartItem.builder().build();
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn("ugo@gmail.com");
        given(cartItemService.removeFromCart(anyLong(),eq("ugo@gmail.com"))).willReturn(ResponseEntity.ok("product successfully deleted"));
        String content = (new ObjectMapper()).writeValueAsString(product);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/remove/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.cartController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(200));
    }
}