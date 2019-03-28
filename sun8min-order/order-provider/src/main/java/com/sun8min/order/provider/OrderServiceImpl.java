package com.sun8min.order.provider;

import com.sun8min.order.api.OrderService;
import com.sun8min.order.dao.OrderDao;
import com.sun8min.order.dao.OrderLineDao;
import com.sun8min.order.entity.Order;
import com.sun8min.order.entity.OrderLine;
import com.sun8min.shop.entity.Product;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service(version = "${service.version}")
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDao orderDao;
    @Autowired
    OrderLineDao orderLineDao;

    @Override
    public int deleteByPrimaryKey(Long orderId) {
        return orderDao.deleteByPrimaryKey(orderId);
    }

    @Override
    public int insert(Order record) {
        return orderDao.insert(record);
    }

    @Override
    public Order selectByPrimaryKey(Long orderId) {
        return orderDao.selectByPrimaryKey(orderId);
    }

    @Override
    public List<Order> selectAll() {
        return orderDao.selectAll();
    }

    @Override
    public int updateByPrimaryKey(Order record) {
        return orderDao.updateByPrimaryKey(record);
    }

    @Override
    public Order findByTradeOrderNo(Long tradeOrderNo) {
        return orderDao.findByTradeOrderNo(tradeOrderNo);
    }

    @Transactional
    @Override
    public Order placeOrder(Long fromUserId, Long toUserId, List<Pair<Product, Integer>> productQuantitiesList, BigDecimal redpacketPayAmount) {
        String tradeOrderNo = UUID.randomUUID().toString().replace("-", "");

        // 订单总金额
        BigDecimal payAmount = BigDecimal.ZERO;
        // 订单项
        for (Pair<Product, Integer> productQuantities : productQuantitiesList) {
            Product product = productQuantities.getLeft();
            Integer quantities = productQuantities.getRight();
            OrderLine orderLine = new OrderLine();
            orderLine.setProductId(product.getProductId());
            orderLine.setProductPrice(product.getProductPrice());
            orderLine.setProductQuantity(quantities.longValue());
            payAmount.add(product.getProductPrice().multiply(new BigDecimal(quantities.longValue())));
            orderLine.setTradeOrderNo(tradeOrderNo);
            orderLineDao.insert(orderLine);
        }

        // 账户交易金额
        BigDecimal capitalPayAmount = payAmount.subtract(redpacketPayAmount);
        // 订单交易记录
        Order order = new Order();
        order.setFromUserId(fromUserId);
        order.setToUserId(toUserId);
        order.setCapitalTradeAmount(capitalPayAmount);
        order.setRedpacketTradeAmount(redpacketPayAmount);
        order.setOrderStatus(Order.OrderStatus.PAYING.getValue());
        order.setTradeOrderNo(tradeOrderNo);
        orderDao.insert(order);
        return order;
    }

}