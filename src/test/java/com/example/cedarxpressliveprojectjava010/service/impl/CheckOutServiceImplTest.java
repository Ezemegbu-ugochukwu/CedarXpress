package com.example.cedarxpressliveprojectjava010.service.impl;

import com.example.cedarxpressliveprojectjava010.dto.CheckOutOneDto;
import com.example.cedarxpressliveprojectjava010.dto.CheckOutTwoDto;
import com.example.cedarxpressliveprojectjava010.entity.*;
import com.example.cedarxpressliveprojectjava010.enums.DeliveryStatus;
import com.example.cedarxpressliveprojectjava010.enums.Payment;
import com.example.cedarxpressliveprojectjava010.exception.CartEmptyException;
import com.example.cedarxpressliveprojectjava010.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CheckOutServiceImplTest {
    @InjectMocks
    private CheckOutServiceImpl checkOutService;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderItemRepository orderItemRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AddressRepository addressRepository;
    @Mock
    private AddressListRepository addressListRepository;


    Cart cart;
    Cart cart2;
    User user;
    User user2;
    AddressList addressList;
    Address address;
    Order order;
    CartItem cartItem;
    OrderItem orderItem;
    CheckOutOneDto checkOutOneDto;
    CheckOutTwoDto checkOutTwoDto;
    CheckOutTwoDto checkOutException;
    Product product;

    Authentication authentication = Mockito.mock(Authentication.class);
    SecurityContext securityContext = Mockito.mock(SecurityContext.class);
    Authentication auth = Mockito.mock(Authentication.class);

    @BeforeEach
    void setup(){
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);


        List<Address> myAddList = new ArrayList<>();
        List<CartItem> cartItemList = new ArrayList<>();
        List<CartItem> emptyCart = new ArrayList<>();
        List<OrderItem> orderItemList = new ArrayList<>();

        user = User.builder().firstName("Darlington").email("email").build();
        user2 = User.builder().firstName("Darlington").email("email").build();

        cart = Cart.builder().customer(user).build();
        cart2 = Cart.builder().customer(user2).build();

        product = Product.builder().productName("Office Chair").build();
        cartItem = CartItem.builder().cart(cart).product(product).unit(2).build();
        addressList = AddressList.builder().customer(user).addressList(myAddList).build();
        address=Address.builder().addressList(addressList).build();

        checkOutOneDto = CheckOutOneDto.builder()
                .state("Edo")
                .address("Tech-Park")
                .city("Benin")
                .phoneNumber("08137559136")
                .zipCode(88970)
                .payment("CARD")
                .cart(cart)
                .build();

        checkOutTwoDto = CheckOutTwoDto.builder()
                .cart(cart).payment("CARD").address(address).build();
        checkOutException = CheckOutTwoDto.builder()
                .cart(cart2).payment("CARD").address(address).build();

        order = Order.builder()
                .deliveryStatus(DeliveryStatus.PENDING)
                .paymentMethod(Payment.CARD)
                .address(address)
                .discount(0.00)
                .deliveryFee(0.00)
                .build();


        orderItem = OrderItem.builder()
                .order(order)
                .unit(cartItem.getUnit())
                .product(cartItem.getProduct())
                .build();

        cartItemList.add(cartItem);
        cartItemList.add(cartItem);

        orderItemList.add(orderItem);
        orderItemList.add(orderItem);

        cart.setCartItems(cartItemList);
        cart2.setCartItems(emptyCart);
        order.setCustomerOrder(orderItemList);

    }

    @DisplayName("Test to make an Order from a customer Cart")
    @Test
    void makeOrderUsingCheckOutOneDto() {

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn(user.getEmail());
        given(userRepository.getUserByEmail(user.getEmail())).willReturn(user);
        given((addressListRepository.findAddressListByCustomer(any()))).willReturn(Optional.of(addressList));
        given(addressListRepository.save(any())).willReturn(addressList);
        given(orderRepository.save(any())).willReturn(order);


        Order finalOrder = checkOutService.makeOrder(checkOutOneDto);

        assertThat(finalOrder).isNotNull();
        assertThat(finalOrder).isEqualTo(order);
    }

    @DisplayName("Test to make order using Already Existing Address")
    @Test
    void makeOrderUsingCheckOutTwoDto() {

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn(user.getEmail());
        given(userRepository.getUserByEmail(user.getEmail())).willReturn(user);
        given(orderRepository.save(any())).willReturn(order);

        Order finalOrder = checkOutService.makeOrder(checkOutTwoDto);

        assertThat(finalOrder).isNotNull();
        assertThat(finalOrder).isEqualTo(order);

    }

    @DisplayName("Should throw empty cart Exception")
    @Test
    void shouldThrowEmptyCartException(){

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn(user.getEmail());
        given(userRepository.getUserByEmail(user2.getEmail())).willReturn(user2);

        assertThatThrownBy(() -> checkOutService.makeOrder(checkOutException)).isInstanceOf(CartEmptyException.class);



    }
}