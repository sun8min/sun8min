package com.sun8min.redpacket.dao;

import com.sun8min.redpacket.entity.RedpacketTradeOrder;
import java.util.List;

public interface RedpacketTradeOrderDao {
    int deleteByPrimaryKey(Long redpacketTradeOrderId);

    int insert(RedpacketTradeOrder record);

    RedpacketTradeOrder selectByPrimaryKey(Long redpacketTradeOrderId);

    List<RedpacketTradeOrder> selectAll();

    int updateByPrimaryKey(RedpacketTradeOrder record);
}