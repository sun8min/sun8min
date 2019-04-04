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
import org.springframework.transaction.annotation.Transactional;

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
 * @since 2019-04-04
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

    @Transactional
    @Override
    public Order placeOrder(Long fromUserId, Long toUserId, List<Pair<Product, Integer>> productQuantitiesList, BigDecimal redpacketPayAmount) {
        System.out.println("全局事务id ：" + RootContext.getXID());
        // 订单编号
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
            orderLine.setProductQuantity(BigInteger.valueOf(quantities));
            payAmount = payAmount.add(product.getProductPrice().multiply(new BigDecimal(quantities.longValue())));
            orderLine.setTradeOrderNo(tradeOrderNo);
            orderLineMapper.insert(orderLine);
        }

        // 账户交易金额
        BigDecimal capitalPayAmount = payAmount.subtract(redpacketPayAmount);
        // 订单交易记录
        Order order = new Order();
        order.setFromUserId(BigInteger.valueOf(fromUserId));
        order.setToUserId(BigInteger.valueOf(toUserId));
        order.setCapitalTradeAmount(capitalPayAmount);
        order.setRedpacketTradeAmount(redpacketPayAmount);
        order.setOrderStatus(Order.OrderStatus.PAYING.getValue());
        order.setTradeOrderNo(tradeOrderNo);
        orderMapper.insert(order);
        return order;
    }
    
}
