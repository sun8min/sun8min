package com.sun8min.seckill.service;

import com.sun8min.order.entity.Order;
import com.sun8min.seckill.dto.PlaceOrderRequestDTO;

public interface SeckillService {
    Order handleSeckill(PlaceOrderRequestDTO placeOrderRequestDTO);
}
