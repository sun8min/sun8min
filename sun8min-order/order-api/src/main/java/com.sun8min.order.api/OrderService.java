package com.sun8min.order.api;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sun8min.order.entity.Order;
import com.sun8min.order.entity.ParentOrder;
import com.sun8min.product.entity.Product;
import com.sun8min.product.entity.Shop;
import org.apache.commons.lang3.tuple.Pair;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author sun8min
 * @since 2019-04-06
 */
public interface OrderService extends IService<Order> {
    /**
     * 根据交易单号查找订单
     *
     * @param tradeOrderNo
     * @return
     */
    Order findByTradeOrderNo(String tradeOrderNo);

    /**
     * 根据父交易单号查找订单
     *
     * @param parentOrderNo
     * @return
     */
    List<Order> findByParentOrderNo(String parentOrderNo);

    /**
     * 下单
     *
     * @return
     */
    Pair<ParentOrder, List<Order>> placeOrder(BigInteger fromUserId, Map<Shop, List<Pair<Product, Long>>> shopProductQuantitiesList);

}
