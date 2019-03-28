package com.sun8min.redpacket.api;

import com.sun8min.redpacket.entity.Redpacket;

import java.math.BigDecimal;
import java.util.List;

public interface RedpacketService {
    int deleteByPrimaryKey(Long redpacketId);

    int insert(Redpacket record);

    Redpacket selectByPrimaryKey(Long redpacketId);

    List<Redpacket> selectAll();

    int updateByPrimaryKey(Redpacket record);

    /**
     * 根据用户id查找红包余额
     * @param userId
     * @return
     */
    BigDecimal findAmountByUserId(Long userId);
}