package com.sun8min.capital.provider;

import com.sun8min.capital.api.CapitalTradeOrderService;
import com.sun8min.capital.dao.CapitalTradeOrderDao;
import com.sun8min.capital.entity.CapitalTradeOrder;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service(version = "${service.version}")
public class CapitalTradeOrderServiceImpl implements CapitalTradeOrderService {

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
}