package com.sun8min.capital.provider;

import com.alibaba.fescar.core.context.RootContext;
import com.sun8min.capital.api.CapitalTradeOrderService;
import com.sun8min.capital.dao.CapitalDao;
import com.sun8min.capital.dao.CapitalTradeOrderDao;
import com.sun8min.capital.entity.Capital;
import com.sun8min.capital.entity.CapitalTradeOrder;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service(version = "${service.version}")
public class CapitalTradeOrderServiceImpl implements CapitalTradeOrderService {

    @Autowired
    CapitalDao capitalDao;
    @Autowired
    CapitalTradeOrderDao capitalTradeOrderDao;

    public int deleteByPrimaryKey(Long capitalTradeOrderId) {
        return capitalTradeOrderDao.deleteByPrimaryKey(capitalTradeOrderId);
    }

    public int insert(CapitalTradeOrder record) {
        return capitalTradeOrderDao.insert(record);
    }

    public CapitalTradeOrder selectByPrimaryKey(Long capitalTradeOrderId) {
        return capitalTradeOrderDao.selectByPrimaryKey(capitalTradeOrderId);
    }

    public List<CapitalTradeOrder> selectAll() {
        return capitalTradeOrderDao.selectAll();
    }

    public int updateByPrimaryKey(CapitalTradeOrder record) {
        return capitalTradeOrderDao.updateByPrimaryKey(record);
    }

    @Transactional
    @Override
    public Boolean trade(String tradeOrderNo, long fromUserId, long toUserId, BigDecimal capitalPayAmount) {
        System.out.println("全局事务id ：" + RootContext.getXID());
        // 账户交易记录
        CapitalTradeOrder capitalTradeOrder = new CapitalTradeOrder();
        capitalTradeOrder.setFromUserId(fromUserId);
        capitalTradeOrder.setToUserId(toUserId);
        capitalTradeOrder.setCapitalTradeAmount(capitalPayAmount);
        capitalTradeOrder.setTradeOrderNo(tradeOrderNo);
        capitalTradeOrderDao.insert(capitalTradeOrder);
        // 账户转出
        Capital fromCapital = capitalDao.findByUserId(fromUserId);
        fromCapital.setCapitalAmount(fromCapital.getCapitalAmount().subtract(capitalPayAmount));
        capitalDao.updateByPrimaryKey(fromCapital);
        // 账户转入
        Capital toCapital = capitalDao.findByUserId(toUserId);
        toCapital.setCapitalAmount(toCapital.getCapitalAmount().add(capitalPayAmount));
        capitalDao.updateByPrimaryKey(toCapital);
        return true;
    }
}