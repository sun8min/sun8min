package com.sun8min.order.dao;

import com.sun8min.order.entity.OrderLine;
import java.util.List;

public interface OrderLineDao {
    int deleteByPrimaryKey(Long orderLineId);

    int insert(OrderLine record);

    OrderLine selectByPrimaryKey(Long orderLineId);

    List<OrderLine> selectAll();

    int updateByPrimaryKey(OrderLine record);
}