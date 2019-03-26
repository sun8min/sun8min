package com.sun8min.capital.dao;

import com.sun8min.capital.entity.CapitalTradeOrder;
import java.util.List;

public interface CapitalTradeOrderDao {
    int deleteByPrimaryKey(Long capitalTradeOrderId);

    int insert(CapitalTradeOrder record);

    CapitalTradeOrder selectByPrimaryKey(Long capitalTradeOrderId);

    List<CapitalTradeOrder> selectAll();

    int updateByPrimaryKey(CapitalTradeOrder record);
}