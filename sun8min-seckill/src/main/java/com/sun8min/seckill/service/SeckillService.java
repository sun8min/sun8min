package com.sun8min.seckill.service;

import com.sun8min.order.entity.ParentOrder;
import com.sun8min.seckill.dto.PlaceOrderRequestDTO;

public interface SeckillService {
    ParentOrder handleSeckill(PlaceOrderRequestDTO placeOrderRequestDTO);
}
