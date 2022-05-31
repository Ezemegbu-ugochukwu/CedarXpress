package com.example.cedarxpressliveprojectjava010.service;

import com.example.cedarxpressliveprojectjava010.dto.OrderDto;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface OrderService {

    ResponseEntity<OrderDto> getSingleOrder(long userId, long orderId);
    List<OrderDto> getOrders(long userId);
}
