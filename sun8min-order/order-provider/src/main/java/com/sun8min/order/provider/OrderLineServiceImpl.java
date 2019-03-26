package com.sun8min.order.provider;

import com.sun8min.order.api.OrderLineService;
import com.sun8min.order.dao.OrderLineDao;
import com.sun8min.order.entity.OrderLine;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service(version = "${service.version}")
public class OrderLineServiceImpl implements OrderLineService {

    @Autowired
    OrderLineDao orderLineDao;

    public int deleteByPrimaryKey(Long orderLineId) {
        return orderLineDao.deleteByPrimaryKey(orderLineId);
    }

    public int insert(OrderLine record) {
        return orderLineDao.insert(record);
    }

    public OrderLine selectByPrimaryKey(Long orderLineId) {
        return orderLineDao.selectByPrimaryKey(orderLineId);
    }

    public List<OrderLine> selectAll() {
        return orderLineDao.selectAll();
    }

    public int updateByPrimaryKey(OrderLine record) {
        return orderLineDao.updateByPrimaryKey(record);
    }
}