package com.example.cedarxpressliveprojectjava010.controller;

import com.example.cedarxpressliveprojectjava010.dto.CheckOutDto;
import com.example.cedarxpressliveprojectjava010.dto.OrderDto;
import com.example.cedarxpressliveprojectjava010.service.CheckOutService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class CheckOutController {
private CheckOutService checkOutService;

    @PostMapping("/checkout")
    public ResponseEntity<OrderDto> checkOut(@RequestBody CheckOutDto checkOutDto){
        return checkOutService.makeOrder(checkOutDto);
    }
}
