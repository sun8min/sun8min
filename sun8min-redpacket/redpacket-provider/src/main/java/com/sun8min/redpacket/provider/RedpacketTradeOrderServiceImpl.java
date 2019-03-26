package com.sun8min.redpacket.provider;

import com.sun8min.redpacket.api.RedpacketTradeOrderService;
import com.sun8min.redpacket.dao.RedpacketTradeOrderDao;
import com.sun8min.redpacket.entity.RedpacketTradeOrder;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service(version = "${service.version}")
public class RedpacketTradeOrderServiceImpl implements RedpacketTradeOrderService {

    @Autowired
    RedpacketTradeOrderDao redpacketTradeOrderDao;

    public int deleteByPrimaryKey(Long redpacketTradeOrderId) {
        return redpacketTradeOrderDao.deleteByPrimaryKey(redpacketTradeOrderId);
    }

    public int insert(RedpacketTradeOrder record) {
        return redpacketTradeOrderDao.insert(record);
    }

    public RedpacketTradeOrder selectByPrimaryKey(Long redpacketTradeOrderId) {
        return redpacketTradeOrderDao.selectByPrimaryKey(redpacketTradeOrderId);
    }

    public List<RedpacketTradeOrder> selectAll() {
        return redpacketTradeOrderDao.selectAll();
    }

    public int updateByPrimaryKey(RedpacketTradeOrder record) {
        return redpacketTradeOrderDao.updateByPrimaryKey(record);
    }
}
