package com.sun8min.redpacket.api;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sun8min.redpacket.entity.RedpacketTradeOrder;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * <p>
 * 用户红包交易表 服务类
 * </p>
 *
 * @author sun8min
 * @since 2019-04-04
 */
public interface RedpacketTradeOrderService extends IService<RedpacketTradeOrder> {

    /**
     * 红包交易
     * @param tradeOrderNo 交易订单号
     * @param fromUserId 转出者
     * @param toUserId 转入者
     * @param redPacketPayAmount 红包交易额
     * @return 交易结果
     */
    Boolean trade(String tradeOrderNo, BigInteger fromUserId, BigInteger toUserId, BigDecimal redPacketPayAmount);
}
