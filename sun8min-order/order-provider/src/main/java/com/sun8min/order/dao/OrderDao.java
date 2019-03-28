package com.sun8min.order.dao;

import com.sun8min.order.entity.Order;
import java.util.List;

public interface OrderDao {
    int deleteByPrimaryKey(Long orderId);

    int insert(Order record);

    Order selectByPrimaryKey(Long orderId);

    List<Order> selectAll();

    int updateByPrimaryKey(Order record);

    Order findByTradeOrderNo(Long tradeOrderNo);
}