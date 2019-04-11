package com.sun8min.order.api;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sun8min.order.entity.Order;
import com.sun8min.product.entity.ProductSnapshot;
import com.sun8min.product.entity.Shop;
import org.apache.commons.lang3.tuple.Pair;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

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
     * @param tradeOrderNo 交易单号
     * @return 订单
     */
    Order findByTradeOrderNo(String tradeOrderNo);

    /**
     * 下单
     *
     * @param fromUserId                    付费用户
     * @param shop                          购买的商店
     * @param productSnapshotQuantitiesList 商品快照列表
     * @param payChannel                    支付渠道
     * @return 订单
     */
    Order placeOrder(BigInteger fromUserId, Shop shop, List<Pair<ProductSnapshot, Long>> productSnapshotQuantitiesList, Integer payChannel);

    /**
     * 账单支付成功
     *
     * @param tradeOrderNo 订单号
     * @param orderPayNo   渠道支付单号
     * @param orderPayTime 渠道支付时间
     * @param version      版本号
     */
    void paySuccess(String tradeOrderNo, String orderPayNo, LocalDateTime orderPayTime, Long version);

}
