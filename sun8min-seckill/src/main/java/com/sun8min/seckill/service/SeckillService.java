package com.sun8min.seckill.service;

import com.sun8min.order.entity.Order;
import com.sun8min.seckill.dto.PlaceOrderRequestDTO;

import java.time.LocalDateTime;

public interface SeckillService {
    /**
     * 创建订单
     *
     * @param placeOrderRequestDTO
     * @param payChannel           支付渠道
     * @return
     */
    Order placeOrder(PlaceOrderRequestDTO placeOrderRequestDTO, Integer payChannel);

    /**
     * 使用账户订单支付
     *
     * @param order
     */
    void payOrder(Order order);

    /**
     * 订单支付成功
     *
     * @param tradeOrderNo
     * @param orderPayNo
     * @param orderPayTime
     * @param version
     */
    void paySuccess(String tradeOrderNo, String orderPayNo, LocalDateTime orderPayTime, Long version);

}
