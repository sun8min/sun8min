package com.sun8min.order.api;

import com.sun8min.order.entity.OrderLine;

import java.util.List;

public interface OrderLineService {
    int deleteByPrimaryKey(Long orderLineId);

    int insert(OrderLine record);

    OrderLine selectByPrimaryKey(Long orderLineId);

    List<OrderLine> selectAll();

    int updateByPrimaryKey(OrderLine record);
}