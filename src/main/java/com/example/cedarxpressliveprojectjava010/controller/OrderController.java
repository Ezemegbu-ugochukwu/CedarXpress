package com.example.cedarxpressliveprojectjava010.controller;

import com.example.cedarxpressliveprojectjava010.dto.OrderDto;
import com.example.cedarxpressliveprojectjava010.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{userId}/order/")
    public List<OrderDto> getOrders(@PathVariable("userId") long userId){
        //TODO: test end point
        return orderService.viewUserOrders(userId);
    }

    @GetMapping("/{userId}/order/{orderId}")
    public ResponseEntity<OrderDto> getSingleOrder(@PathVariable("userId") long userId,
                                                 @PathVariable("orderId") long orderId){
        //TODO: test end point
        return orderService.viewParticularUserOrder(userId, orderId);

    }


}
