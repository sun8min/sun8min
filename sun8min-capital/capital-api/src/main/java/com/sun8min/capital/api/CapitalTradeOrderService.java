package com.sun8min.capital.api;

import com.sun8min.capital.entity.CapitalTradeOrder;

import java.math.BigDecimal;
import java.util.List;

public interface CapitalTradeOrderService {
    int deleteByPrimaryKey(Long capitalTradeOrderId);

    int insert(CapitalTradeOrder record);

    CapitalTradeOrder selectByPrimaryKey(Long capitalTradeOrderId);

    List<CapitalTradeOrder> selectAll();

    int updateByPrimaryKey(CapitalTradeOrder record);

    /**
     * 账户交易
     * @param tradeOrderNo 交易订单
     * @param fromUserId 转出账户
     * @param toUserId 转入账户
     * @param capitalTradeAmount 账户交易额
     * @return 交易结果
     */
    Boolean trade(String tradeOrderNo, long fromUserId, long toUserId, BigDecimal capitalTradeAmount);
}