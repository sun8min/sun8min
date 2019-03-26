package com.sun8min.order.provider;

import com.sun8min.order.api.OrderService;
import com.sun8min.order.dao.OrderDao;
import com.sun8min.order.entity.Order;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service(version = "${service.version}")
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDao orderDao;

    public int deleteByPrimaryKey(Long orderId) {
        return orderDao.deleteByPrimaryKey(orderId);
    }

    public int insert(Order record) {
        return orderDao.insert(record);
    }

    public Order selectByPrimaryKey(Long orderId) {
        return orderDao.selectByPrimaryKey(orderId);
    }

    public List<Order> selectAll() {
        return orderDao.selectAll();
    }

    public int updateByPrimaryKey(Order record) {
        return orderDao.updateByPrimaryKey(record);
    }
}