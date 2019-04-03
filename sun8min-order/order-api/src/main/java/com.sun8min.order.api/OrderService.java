package com.sun8min.order.api;

import com.sun8min.order.entity.Order;
import com.sun8min.product.entity.Product;
import org.apache.commons.lang3.tuple.Pair;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {
    int deleteByPrimaryKey(Long orderId);

    int insert(Order record);

    Order selectByPrimaryKey(Long orderId);

    List<Order> selectAll();

    int updateByPrimaryKey(Order record);

    /**
     * 根据交易单号查找订单
     * @param tradeOrderNo
     * @return
     */
    Order findByTradeOrderNo(String tradeOrderNo);

    /**
     * 下单
     * @return
     */
    Order placeOrder(Long fromUserId, Long toUserId, List<Pair<Product, Integer>> productQuantitiesList, BigDecimal redpacketPayAmount);
}