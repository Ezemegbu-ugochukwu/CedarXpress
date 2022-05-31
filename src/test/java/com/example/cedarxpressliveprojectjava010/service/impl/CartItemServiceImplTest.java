package com.example.cedarxpressliveprojectjava010.service.impl;

import com.example.cedarxpressliveprojectjava010.dto.ProductDto;
import com.example.cedarxpressliveprojectjava010.dto.request.AlterProductQuantityRequest;
import com.example.cedarxpressliveprojectjava010.dto.response.CartItemDto;
import com.example.cedarxpressliveprojectjava010.entity.Cart;
import com.example.cedarxpressliveprojectjava010.entity.CartItem;
import com.example.cedarxpressliveprojectjava010.entity.Product;
import com.example.cedarxpressliveprojectjava010.entity.User;
import com.example.cedarxpressliveprojectjava010.enums.Gender;
import com.example.cedarxpressliveprojectjava010.exception.CartNotFoundException;
import com.example.cedarxpressliveprojectjava010.repository.CartItemRepository;
import com.example.cedarxpressliveprojectjava010.repository.CartRepository;
import com.example.cedarxpressliveprojectjava010.repository.ProductRepository;
import com.example.cedarxpressliveprojectjava010.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartItemServiceImplTest {

    @Mock
    UserRepository userRepository;

    @Mock
    ProductRepository productRepository;

    @Mock
    CartRepository cartRepository;

    @Mock
    CartItemRepository cartItemRepository;

    @Mock
    ModelMapper mapper;

    @InjectMocks
    CartItemServiceImpl cartItemService;


    static User user;
    static Product product;
    static Authentication auth;

    @BeforeAll
    static void setUp(){
        auth = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);

         user = User.builder()
                .firstName("ugo")
                .lastName("eze")
                .email("ugo@gmail.com")
                .password("1234")
                .gender(Gender.MALE)
                .build();

        product = Product.builder()
                .productName("shoe")
                .description("a black shoe")
                .price(50.00)
                .build();
    }

    @Test
    void addToCart() {
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
                .build();

        Cart cart = Cart.builder()
                .cartItems(new ArrayList<CartItem>())
                .customer(user)
                .build();

        CartItem cartItem = com.example.cedarxpressliveprojectjava010.entity.CartItem.builder().build();

        String loggedInEmail = "ugo@gmail.com";

        when(userRepository.getUserByEmail(any())).thenReturn(user);
        when(productRepository.findById(any())).thenReturn(Optional.of(product));
        when(cartRepository.findCartByCustomer(user)).thenReturn(Optional.of(cart));
        when(cartItemRepository.findCartItemByCartAndProduct(any(), any())).thenReturn(Optional.of(cartItem));


        var ans = cartItemService.addToCart(product.getId());
        assertEquals(ans.getStatusCode(), HttpStatus.OK);
        assertEquals(ans.getBody(), cart);

    }

    @Test
    void canAddProductToCartWhenCartIsEmpty(){
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
                .build();

        Cart cart = Cart.builder()
                .cartItems(new ArrayList<CartItem>())
                .customer(user)
                .build();

        CartItem cartItem = new CartItem();

        when(userRepository.getUserByEmail(any())).thenReturn(user);
        when(productRepository.findById(any())).thenReturn(Optional.of(product));

        when(cartRepository.findCartByCustomer(user)).thenReturn(Optional.empty());
        when(cartItemRepository.save(any())).thenReturn(cartItem);

        var ans = cartItemService.addToCart(product.getId());
        assertEquals(ans.getStatusCode(), HttpStatus.OK);
        assertNotNull(ans.getBody());
        assertEquals(ans.getBody().getCustomer(), user);
        assertEquals(ans.getBody().getCustomer().getEmail(), user.getEmail());
        assertNotNull(ans.getBody().getCartItems());
        assertEquals(ans.getBody().getCartItems().size(), 1);



    }
    @Test
    void canAddProductToCartWhenCartItemIsEmpty(){
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
                .build();

        Cart cart = Cart.builder()
                .cartItems(new ArrayList<CartItem>())
                .customer(user)
                .build();

        when(userRepository.getUserByEmail(any())).thenReturn(user);
        when(productRepository.findById(any())).thenReturn(Optional.of(product));

        when(cartRepository.findCartByCustomer(user)).thenReturn(Optional.of(cart));
        when(cartItemRepository.findCartItemByCartAndProduct(any(), any())).thenReturn(Optional.empty());


        var ans = cartItemService.addToCart(product.getId());
        assertEquals(ans.getStatusCode(), HttpStatus.OK);
        assertNotNull(ans.getBody());
        assertEquals(ans.getBody().getCustomer(), user);
        assertEquals(ans.getBody().getCustomer().getEmail(), user.getEmail());
        assertNotNull(ans.getBody().getCartItems());
        assertEquals(ans.getBody().getCartItems().size(), 1);
        assertEquals(ans.getBody().getCartItems().get(0).getProduct(), product);


    }

    @Test
    void removeFromCart() {
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
                .build();

        Cart cart = Cart.builder()
                .cartItems(new ArrayList<CartItem>())
                .customer(user)
                .build();
        when(userRepository.findUserByEmail(any())).thenReturn(Optional.of(user));
        when(cartRepository.findCartByCustomer(user)).thenReturn(Optional.of(cart));
        var result = cartItemService.removeFromCart(product.getId(), user.getEmail());
        Assertions.assertEquals(result.getStatusCode(), HttpStatus.OK);
        Assertions.assertEquals(result.getBody(), "product successfully deleted" );

    }
    @Test
    void canCartItemDoesnotExist() {
        User user = User.builder()
                .firstName("ugo")
                .lastName("eze")
                .email("ugo@gmail.com")
                .password("1234")
                .gender(Gender.MALE)
                .build();


        when(userRepository.findUserByEmail(any())).thenReturn(Optional.of(user));
        when(cartRepository.findCartByCustomer(user)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> cartItemService.clearCart())
                .isInstanceOf(CartNotFoundException.class)
                .hasMessage("cart does not exist!!");
    }
    @Test
    void canClear(){
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
                .build();

        Cart cart = Cart.builder()
                .cartItems(new ArrayList<CartItem>())
                .customer(user)
                .build();

        when(userRepository.findUserByEmail(any())).thenReturn(Optional.of(user));
        when(cartRepository.findCartByCustomer(user)).thenReturn(Optional.of(cart));
        var ans = cartItemService.clearCart();
        assertEquals(ans.getStatusCode(), HttpStatus.OK);
    }

    @DisplayName("Test for successful altering of cart product quantity")
    @Test
    public void shouldReturnCartItemDtoWithAlteredValue() throws JsonProcessingException {
        Cart cart = Cart.builder()
                .cartItems(new ArrayList<CartItem>())
                .customer(user)
                .build();

        Product product = Product.builder()
                .productName("shoe")
                .description("a black shoe")
                .price(50.00)
                .build();
        ProductDto productDto = ProductDto.builder()
                .productName("shoe")
                .description("a black shoe")
                .price(50.00)
                .build();

        CartItem cartItem = CartItem.builder()
                .product(product)
                .unit(1)
                .build();
        CartItem cartItem2 = CartItem.builder()
                .product(product)
                .unit(2)
                .build();
        CartItemDto cartItemDto = CartItemDto.builder()
                .productDto(productDto)
                .unit(2)
                .build();

        when(auth.getName()).thenReturn(user.getEmail());
        when(userRepository.findUserByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(cartRepository.findCartByCustomer(user)).thenReturn(Optional.of(cart));
        when(cartItemRepository.findCartItemByCartAndProductId(cart, 1L)).thenReturn(Optional.of(cartItem));
        when(cartItemRepository.save(cartItem)).thenReturn(cartItem2);
        when(mapper.map(cartItem2.getProduct(), ProductDto.class)).thenReturn(productDto);

        AlterProductQuantityRequest request = new AlterProductQuantityRequest(1L, 2);
        ResponseEntity<CartItemDto> response = cartItemService.alterProductQuantity(request);

        String expectedJSON = new ObjectMapper().writeValueAsString(cartItemDto);
        String responseJSON = new ObjectMapper().writeValueAsString(response.getBody());

        assertThat(responseJSON).isEqualTo(expectedJSON);


    }

    @DisplayName("Test for setting cart item quantity to zero")
    @Test
    public void shouldThrowException(){
        AlterProductQuantityRequest request = new AlterProductQuantityRequest(1L, 0);
        Throwable thrown = catchThrowable(() -> cartItemService.alterProductQuantity(request));
        assertThat(thrown).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Product quantity cant be zero");
    }
}