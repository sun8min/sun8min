package com.sun8min.order.api;

import com.sun8min.order.entity.Order;

import java.util.List;

public interface OrderService {
    int deleteByPrimaryKey(Long orderId);

    int insert(Order record);

    Order selectByPrimaryKey(Long orderId);

    List<Order> selectAll();

    int updateByPrimaryKey(Order record);
}