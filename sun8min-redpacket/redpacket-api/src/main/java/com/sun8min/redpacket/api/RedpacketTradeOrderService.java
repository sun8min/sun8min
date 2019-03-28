package com.sun8min.redpacket.api;

import com.sun8min.redpacket.entity.RedpacketTradeOrder;

import java.math.BigDecimal;
import java.util.List;

public interface RedpacketTradeOrderService {
    int deleteByPrimaryKey(Long redpacketTradeOrderId);

    int insert(RedpacketTradeOrder record);

    RedpacketTradeOrder selectByPrimaryKey(Long redpacketTradeOrderId);

    List<RedpacketTradeOrder> selectAll();

    int updateByPrimaryKey(RedpacketTradeOrder record);

    /**
     * 红包交易
     * @param tradeOrderNo 交易订单号
     * @param fromUserId 转出者
     * @param toUserId 转入者
     * @param redPacketPayAmount 红包交易额
     * @return 交易结果
     */
    Boolean trade(String tradeOrderNo, Long fromUserId, Long toUserId, BigDecimal redPacketPayAmount);
}