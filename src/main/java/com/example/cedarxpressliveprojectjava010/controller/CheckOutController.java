package com.example.cedarxpressliveprojectjava010.controller;

import com.example.cedarxpressliveprojectjava010.dto.CheckOutOneDto;
import com.example.cedarxpressliveprojectjava010.dto.CheckOutTwoDto;
import com.example.cedarxpressliveprojectjava010.entity.Order;
import com.example.cedarxpressliveprojectjava010.service.CheckOutService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class CheckOutController {
private CheckOutService checkOutService;

    @PostMapping("/checkout1")
    public Order checkOutWithNewAddress(@RequestBody CheckOutOneDto checkOutOneDto) {
        return checkOutService.makeOrder(checkOutOneDto);
    }

    @PostMapping("/checkout2")
    public Order checkOutWithExistingAddress(@RequestBody CheckOutTwoDto checkOutTwoDto){
        return checkOutService.makeOrder(checkOutTwoDto);
    }


}
