package com.sun8min.redpacket.provider;

import com.alibaba.fescar.core.context.RootContext;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun8min.redpacket.api.RedpacketTradeOrderService;
import com.sun8min.redpacket.entity.Redpacket;
import com.sun8min.redpacket.entity.RedpacketTradeOrder;
import com.sun8min.redpacket.mapper.RedpacketMapper;
import com.sun8min.redpacket.mapper.RedpacketTradeOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * <p>
 * 用户红包交易表 服务实现类
 * </p>
 *
 * @author sun8min
 * @since 2019-04-04
 */
@Service
public class RedpacketTradeOrderServiceImpl extends ServiceImpl<RedpacketTradeOrderMapper, RedpacketTradeOrder> implements RedpacketTradeOrderService {

    @Autowired
    RedpacketTradeOrderMapper redpacketTradeOrderMapper;
    @Autowired
    RedpacketMapper redpacketMapper;

    @Transactional
    @Override
    public Boolean trade(String tradeOrderNo, BigInteger fromUserId, BigInteger toUserId, BigDecimal redPacketPayAmount) {
        System.out.println("全局事务id ：" + RootContext.getXID());
        // 红包交易记录
        RedpacketTradeOrder redpacketTradeOrder = new RedpacketTradeOrder();
        redpacketTradeOrder.setFromUserId(fromUserId);
        redpacketTradeOrder.setToUserId(toUserId);
        redpacketTradeOrder.setRedpacketTradeAmount(redPacketPayAmount);
        redpacketTradeOrder.setTradeOrderNo(tradeOrderNo);
        redpacketTradeOrderMapper.insert(redpacketTradeOrder);
        // 红包转出
        Redpacket fromRedpacket = redpacketMapper.findByUserId(fromUserId);
        fromRedpacket.setRedpacketAmount(fromRedpacket.getRedpacketAmount().subtract(redPacketPayAmount));
        redpacketMapper.updateById(fromRedpacket);
        // 红包转入
        Redpacket toRedpacket = redpacketMapper.findByUserId(toUserId);
        toRedpacket.setRedpacketAmount(toRedpacket.getRedpacketAmount().add(redPacketPayAmount));
        redpacketMapper.updateById(toRedpacket);
        return true;
    }
}
