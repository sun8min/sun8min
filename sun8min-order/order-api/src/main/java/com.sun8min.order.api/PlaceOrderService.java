package com.sun8min.order.api;

import com.sun8min.order.entity.Order;
import com.sun8min.order.entity.OrderLine;

import java.util.List;

public interface PlaceOrderService {
    /**
     * 下单
     * @return
     */
    Boolean placeOrder(Order order, List<OrderLine> orderLines);
}
