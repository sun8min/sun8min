package com.sun8min.order.provider;

import com.alibaba.fescar.core.context.RootContext;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun8min.order.api.OrderService;
import com.sun8min.order.entity.Order;
import com.sun8min.order.entity.OrderLine;
import com.sun8min.order.mapper.OrderLineMapper;
import com.sun8min.order.mapper.OrderMapper;
import com.sun8min.product.entity.Product;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author sun8min
 * @since 2019-04-06
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    OrderMapper orderMapper;
    @Autowired
    OrderLineMapper orderLineMapper;

    @Override
    public Order findByTradeOrderNo(String tradeOrderNo) {
        return orderMapper.findByTradeOrderNo(tradeOrderNo);
    }

    @Override
    public Order placeOrder(BigInteger fromUserId, BigInteger toUserId, List<Pair<Product, Long>> productQuantitiesList) {
        System.out.println("全局事务id ：" + RootContext.getXID());
        // 订单编号
        String tradeOrderNo = UUID.randomUUID().toString().replace("-", "");

        // 订单总金额
        BigDecimal payAmount = BigDecimal.ZERO;
        // 订单项
        for (Pair<Product, Long> productQuantities : productQuantitiesList) {
            Product product = productQuantities.getLeft();
            Long quantities = productQuantities.getRight();
            OrderLine orderLine = new OrderLine();
            orderLine.setProductSnapshotId(product.getProductId());
            orderLine.setProductQuantity(quantities);
            payAmount = payAmount.add(product.getProductPrice().multiply(new BigDecimal(quantities)));
            orderLine.setTradeOrderNo(tradeOrderNo);
            orderLineMapper.insert(orderLine);
        }

        // 订单交易记录
        Order order = new Order();
        order.setFromUserId(fromUserId);
        order.setToUserId(toUserId);
        order.setOrderTradeAmount(payAmount);
        order.setOrderStatus(Order.OrderStatus.PAYING.getCode());
        order.setTradeOrderNo(tradeOrderNo);
        orderMapper.insert(order);
        return order;
    }
}
