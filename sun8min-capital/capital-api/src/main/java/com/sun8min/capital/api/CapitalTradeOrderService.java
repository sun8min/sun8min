package com.sun8min.capital.api;

import com.sun8min.capital.entity.CapitalTradeOrder;

import java.util.List;

public interface CapitalTradeOrderService {
    int deleteByPrimaryKey(Long capitalTradeOrderId);

    int insert(CapitalTradeOrder record);

    CapitalTradeOrder selectByPrimaryKey(Long capitalTradeOrderId);

    List<CapitalTradeOrder> selectAll();

    int updateByPrimaryKey(CapitalTradeOrder record);
}