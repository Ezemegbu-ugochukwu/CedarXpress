package com.example.cedarxpressliveprojectjava010.service;

import com.example.cedarxpressliveprojectjava010.dto.CheckOutOneDto;
import com.example.cedarxpressliveprojectjava010.dto.CheckOutTwoDto;
import com.example.cedarxpressliveprojectjava010.entity.Order;

public interface CheckOutService {
    Order makeOrder(CheckOutOneDto checkOutOneDto);
    Order makeOrder(CheckOutTwoDto checkOutTwoDto);
}
