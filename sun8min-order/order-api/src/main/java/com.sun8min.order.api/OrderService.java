package com.sun8min.order.api;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sun8min.order.entity.Order;
import com.sun8min.product.entity.Product;
import org.apache.commons.lang3.tuple.Pair;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author sun8min
 * @since 2019-04-04
 */
public interface OrderService extends IService<Order> {

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
