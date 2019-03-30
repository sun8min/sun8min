package com.sun8min.redpacket.provider;

import com.alibaba.fescar.core.context.RootContext;
import com.sun8min.redpacket.api.RedpacketTradeOrderService;
import com.sun8min.redpacket.dao.RedpacketDao;
import com.sun8min.redpacket.dao.RedpacketTradeOrderDao;
import com.sun8min.redpacket.entity.Redpacket;
import com.sun8min.redpacket.entity.RedpacketTradeOrder;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service(version = "${service.version}")
public class RedpacketTradeOrderServiceImpl implements RedpacketTradeOrderService {

    @Autowired
    RedpacketTradeOrderDao redpacketTradeOrderDao;
    @Autowired
    RedpacketDao redpacketDao;

    @Override
    public int deleteByPrimaryKey(Long redpacketTradeOrderId) {
        return redpacketTradeOrderDao.deleteByPrimaryKey(redpacketTradeOrderId);
    }

    @Override
    public int insert(RedpacketTradeOrder record) {
        return redpacketTradeOrderDao.insert(record);
    }

    @Override
    public RedpacketTradeOrder selectByPrimaryKey(Long redpacketTradeOrderId) {
        return redpacketTradeOrderDao.selectByPrimaryKey(redpacketTradeOrderId);
    }

    @Override
    public List<RedpacketTradeOrder> selectAll() {
        return redpacketTradeOrderDao.selectAll();
    }

    @Override
    public int updateByPrimaryKey(RedpacketTradeOrder record) {
        return redpacketTradeOrderDao.updateByPrimaryKey(record);
    }

    @Override
    @Transactional
    public Boolean trade(String tradeOrderNo, Long fromUserId, Long toUserId, BigDecimal redPacketPayAmount) {
        System.out.println("全局事务id ：" + RootContext.getXID());
        // 红包交易记录
        RedpacketTradeOrder redpacketTradeOrder = new RedpacketTradeOrder();
        redpacketTradeOrder.setFromUserId(fromUserId);
        redpacketTradeOrder.setToUserId(toUserId);
        redpacketTradeOrder.setRedpacketTradeAmount(redPacketPayAmount);
        redpacketTradeOrder.setTradeOrderNo(tradeOrderNo);
        redpacketTradeOrderDao.insert(redpacketTradeOrder);
        // 红包转出
        Redpacket fromRedpacket = redpacketDao.findByUserId(fromUserId);
        fromRedpacket.setRedpacketAmount(fromRedpacket.getRedpacketAmount().subtract(redPacketPayAmount));
        redpacketDao.updateByPrimaryKey(fromRedpacket);
        // 红包转入
        Redpacket toRedpacket = redpacketDao.findByUserId(toUserId);
        toRedpacket.setRedpacketAmount(toRedpacket.getRedpacketAmount().add(redPacketPayAmount));
        redpacketDao.updateByPrimaryKey(fromRedpacket);
        return true;
    }
}
