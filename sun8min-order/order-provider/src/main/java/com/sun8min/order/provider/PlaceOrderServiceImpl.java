package com.sun8min.order.provider;

import com.sun8min.order.api.OrderLineService;
import com.sun8min.order.api.OrderService;
import com.sun8min.order.api.PlaceOrderService;
import com.sun8min.order.entity.Order;
import com.sun8min.order.entity.OrderLine;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(version = "${service.version}")
public class PlaceOrderServiceImpl implements PlaceOrderService {

    @Autowired
    OrderService orderService;
    @Autowired
    OrderLineService orderLineService;

    @Transactional
    public Boolean placeOrder(Order order, List<OrderLine> orderLines) {
        orderService.insert(order);
        orderLines.forEach(orderLine -> orderLineService.insert(orderLine));
        return true;
    }
}
