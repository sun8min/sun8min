package com.sun8min.order.provider;

import com.alibaba.fescar.core.context.RootContext;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun8min.order.api.OrderService;
import com.sun8min.order.entity.Order;
import com.sun8min.order.entity.OrderLine;
import com.sun8min.order.entity.ParentOrder;
import com.sun8min.order.mapper.OrderLineMapper;
import com.sun8min.order.mapper.OrderMapper;
import com.sun8min.order.mapper.ParentOrderMapper;
import com.sun8min.product.entity.Product;
import com.sun8min.product.entity.Shop;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author sun8min
 * @since 2019-04-06
 */
@Service(version = "${service.version}")
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Resource
    OrderMapper orderMapper;
    @Resource
    OrderLineMapper orderLineMapper;
    @Resource
    ParentOrderMapper parentOrderMapper;

    @Override
    public Order findByTradeOrderNo(String tradeOrderNo) {
        return orderMapper.findByTradeOrderNo(tradeOrderNo);
    }

    @Override
    public List<Order> findByParentOrderNo(String parentOrderNo) {
        return orderMapper.findByParentOrderNo(parentOrderNo);
    }

    @Override
    public Pair<ParentOrder, List<Order>> placeOrder(BigInteger fromUserId, Map<Shop, List<Pair<Product, Long>>> shopProductQuantitiesList) {
        System.out.println("全局事务id ：" + RootContext.getXID());

        // 主订单编号
        String parentOrderNo = UUID.randomUUID().toString().replace("-", "");
        // 主订单保存
        ParentOrder parentOrder = new ParentOrder()
                .setFromUserId(fromUserId)
                .setParentOrderNo(parentOrderNo);
        parentOrderMapper.insert(parentOrder);

        List<Order> orderList = new ArrayList<>();
        // 子订单与子订单商品项的保存
        shopProductQuantitiesList.forEach((shop, productList) -> {
                    // 子订单编号
                    String tradeOrderNo = UUID.randomUUID().toString().replace("-", "");
                    // 子订单总金额
                    BigDecimal payAmount = BigDecimal.ZERO;
                    // 子订单商品项
                    for (Pair<Product, Long> productQuantities : productList) {
                        Product product = productQuantities.getLeft();
                        Long quantities = productQuantities.getRight();
                        OrderLine orderLine = new OrderLine()
                                .setProductSnapshotId(product.getProductId())
                                .setProductQuantity(quantities);
                        // 商品价格加入计算
                        payAmount = payAmount.add(product.getProductPrice().multiply(new BigDecimal(quantities)));
                        orderLine.setTradeOrderNo(tradeOrderNo);
                        orderLineMapper.insert(orderLine);
                    }
                    // 订单交易记录
                    Order order = new Order()
                            .setFromUserId(fromUserId)
                            .setToUserId(shop.getUserId())
                            .setShopId(shop.getShopId())
                            .setOrderPayChannel(Order.orderPayChannel.ACCOUNT.getCode())
                            .setOrderTradeAmount(payAmount)
                            .setOrderStatus(Order.OrderStatus.PAYING.getCode())
                            .setTradeOrderNo(tradeOrderNo)
                            .setParentOrderNo(parentOrderNo);
                    orderMapper.insert(order);
                    orderList.add(order);
                }
        );
        return new ImmutablePair<>(parentOrder, orderList);
    }
}
