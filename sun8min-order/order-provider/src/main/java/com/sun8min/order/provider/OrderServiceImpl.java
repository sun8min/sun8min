package com.sun8min.order.provider;

import com.alibaba.fescar.core.context.RootContext;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun8min.base.exception.MyException;
import com.sun8min.order.api.OrderService;
import com.sun8min.order.entity.Order;
import com.sun8min.order.entity.OrderLine;
import com.sun8min.order.mapper.OrderLineMapper;
import com.sun8min.order.mapper.OrderMapper;
import com.sun8min.product.entity.ProductSnapshot;
import com.sun8min.product.entity.Shop;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
@Service(version = "${service.version}")
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Resource
    OrderMapper orderMapper;
    @Resource
    OrderLineMapper orderLineMapper;

    @Override
    public Order findByTradeOrderNo(String tradeOrderNo) {
        return orderMapper.findByTradeOrderNo(tradeOrderNo);
    }

    @Override
    public void paySuccess(String tradeOrderNo, String orderPayNo, LocalDateTime orderPayTime, Long version) {
        log.info("全局事务id ：" + RootContext.getXID());
        Integer updateRows = orderMapper.paySuccess(tradeOrderNo, orderPayNo, orderPayTime, version, Order.OrderStatus.PAY_CONFIRMED.getCode());
        if (updateRows < 1) throw new MyException("订单状态修改失败");
    }

    @Transactional
    @Override
    public Order placeOrder(BigInteger fromUserId, Shop shop, List<Pair<ProductSnapshot, Long>> productSnapshotQuantitiesList, Integer payChannel) {
        log.info("全局事务id ：" + RootContext.getXID());

        // 订单编号
        String tradeOrderNo = UUID.randomUUID().toString().replace("-", "");
        // 订单总金额
        BigDecimal payAmount = BigDecimal.ZERO;
        List<OrderLine> orderLines = new ArrayList<>();
        // 订单商品项
        for (Pair<ProductSnapshot, Long> productSnapshotQuantities : productSnapshotQuantitiesList) {
            ProductSnapshot productSnapshot = productSnapshotQuantities.getLeft();
            Long quantities = productSnapshotQuantities.getRight();
            OrderLine orderLine = new OrderLine()
                    .setProductSnapshotId(productSnapshot.getProductSnapshotId())
                    .setProductQuantity(quantities);
            // 商品价格加入计算
            payAmount = payAmount.add(productSnapshot.getProductPrice().multiply(new BigDecimal(quantities)));
            orderLine.setTradeOrderNo(tradeOrderNo);
            orderLineMapper.insert(orderLine);
            orderLines.add(orderLine);
        }
        // 订单交易记录
        Order order = new Order()
                .setFromUserId(fromUserId)
                .setToUserId(shop.getUserId())
                .setShopId(shop.getShopId())
                .setOrderPayChannel(Order.OrderPayChannel.ACCOUNT.getCode())
                .setOrderTradeAmount(payAmount)
                .setOrderStatus(Order.OrderStatus.WAIT_PAY.getCode())
                .setTradeOrderNo(tradeOrderNo)
                .setOrderPayChannel(payChannel);
        orderMapper.insert(order);
        // 根据order_id查找order，否则version为null
        Order foundOrder = orderMapper.selectById(order.getOrderId()).setOrderLines(orderLines);
        return foundOrder;
    }
}
