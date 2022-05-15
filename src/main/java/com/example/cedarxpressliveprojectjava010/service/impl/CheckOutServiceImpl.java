package com.example.cedarxpressliveprojectjava010.service.impl;

import com.example.cedarxpressliveprojectjava010.dto.CheckOutOneDto;
import com.example.cedarxpressliveprojectjava010.dto.CheckOutTwoDto;
import com.example.cedarxpressliveprojectjava010.entity.*;
import com.example.cedarxpressliveprojectjava010.enums.AddressType;
import com.example.cedarxpressliveprojectjava010.enums.DeliveryStatus;
import com.example.cedarxpressliveprojectjava010.exception.CartEmptyException;
import com.example.cedarxpressliveprojectjava010.repository.*;
import com.example.cedarxpressliveprojectjava010.service.CheckOutService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class CheckOutServiceImpl implements CheckOutService {
    private OrderRepository orderRepository;
    private OrderItemRepository orderItemRepository;
    private UserRepository userRepository;
    private AddressRepository addressRepository;
    private AddressListRepository addressListRepository;
    private ModelMapper modelMapper;


    @Override
    public Order makeOrder(CheckOutOneDto checkOutOneDto) {
        String loggedInEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Cart cart = checkOutOneDto.getCart();
        if(cart.getCartItems().size() < 1) throw new CartEmptyException("Cart is Empty");
        User user = userRepository.getUserByEmail(loggedInEmail);

        AddressList addressList = null;

        Optional<AddressList> optionalAddressList =
                addressListRepository.findAddressListByCustomer(user);


        if(optionalAddressList.isEmpty()){
            addressList = AddressList.builder()
                    .customer(user)
                    .build();
            addressListRepository.save(addressList);
        }else {
            addressList = optionalAddressList.get();
        }

        //address;
        Address address = Address.builder()
                .state(checkOutOneDto.getState())
                .address(checkOutOneDto.getAddress())
                .city(checkOutOneDto.getCity())
                .phoneNumber(checkOutOneDto.getPhoneNumber())
                .isDefault(false)
                .addressType(AddressType.SHIPPING)
                .zipCode(checkOutOneDto.getZipCode())
                .addressList(addressList)
                .build();
        addressRepository.save(address);
        addressList.getAddressList().add(address);
        addressListRepository.save(addressList);

        Order order = Order.builder().customer(user).build();
        order = orderRepository.save(order);

        List<OrderItem> orderItems = this.convertCartToOrderItemList(cart,order);

        order.setCustomerOrder(orderItems);
        order.setPaymentMethod(checkOutOneDto.getPaymentMethod());
        order.setAddress(address);
        order.setDiscount(0.0);
        order.setDeliveryFee(0.00);
        order.setDeliveryStatus(DeliveryStatus.PENDING);

        return orderRepository.save(order);
    }

    private List<OrderItem> convertCartToOrderItemList(Cart cart,Order order){
        List<OrderItem> orderItems = new ArrayList<>();

        for(CartItem each: cart.getCartItems()){
            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(each.getProduct())
                    .unit(each.getUnit())
                    .build();

            orderItemRepository.save(orderItem);
            orderItems.add(orderItem);
        }
        return orderItems;
    }

    @Override
    public Order makeOrder(CheckOutTwoDto checkOutTwoDto) {
        String loggedInEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.getUserByEmail(loggedInEmail);

        Cart cart = checkOutTwoDto.getCart();
        if(cart.getCartItems().size() < 1) throw new CartEmptyException("Cart is Empty");
        Order order = Order.builder().customer(user).build();
        order = orderRepository.save(order);

        List<OrderItem> orderItems = this.convertCartToOrderItemList(cart,order);

        order.setCustomerOrder(orderItems);
        order.setPaymentMethod(checkOutTwoDto.getPaymentMethod());
        order.setAddress(checkOutTwoDto.getAddress());
        order.setDiscount(0.0);
        order.setDeliveryFee(0.00);
        order.setDeliveryStatus(DeliveryStatus.PENDING);

        return  orderRepository.save(order);

    }
}
