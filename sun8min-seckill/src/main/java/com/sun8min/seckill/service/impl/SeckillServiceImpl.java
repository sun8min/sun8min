package com.sun8min.seckill.service.impl;

import com.alibaba.fescar.core.context.RootContext;
import com.alibaba.fescar.spring.annotation.GlobalTransactional;
import com.sun8min.account.api.AccountTradeOrderService;
import com.sun8min.order.api.OrderService;
import com.sun8min.order.entity.Order;
import com.sun8min.seckill.dto.PlaceOrderRequestDTO;
import com.sun8min.seckill.service.SeckillService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SeckillServiceImpl implements SeckillService {

    @Reference(version = "${service.version}")
    OrderService orderService;
    @Reference(version = "${service.version}")
    AccountTradeOrderService accountTradeOrderService;

    private boolean flag;

    @Override
    @GlobalTransactional(timeoutMills = 300000, name = "seckill-gts-fescar-example")
    public Order placeOrder(PlaceOrderRequestDTO placeOrderRequestDTO) {
        // 分布式事务
        log.info("开始全局事务，XID = " + RootContext.getXID());
        // 1.下单
        Order order = orderService.placeOrder(
                placeOrderRequestDTO.getFromUserId(),
                placeOrderRequestDTO.getShop(),
                placeOrderRequestDTO.getProductSnapshotQuantitiesList()
        );
        // TODO 2.减库存

        //打开注释测试事务发生异常后，全局回滚功能
//        if (!flag) {
//            throw new RuntimeException("测试抛异常后，分布式事务回滚！");
//        }
        return order;
    }

    @Override
    @GlobalTransactional(timeoutMills = 300000, name = "seckill-gts-fescar-example")
    public void payOrder(Order order) {
        if (order == null) return;
        // 账户转账
        accountTradeOrderService.trade(
                order.getTradeOrderNo(),
                order.getFromUserId(),
                order.getToUserId(),
                order.getOrderTradeAmount()
        );
        // 账单状态修改支付成功
        orderService.paySuccess(order.getTradeOrderNo(), order.getVersion());
        //打开注释测试事务发生异常后，全局回滚功能
//        if (!flag) {
//            throw new RuntimeException("测试抛异常后，分布式事务回滚！");
//        }
    }

    @Override
    public void paySuccess(String tradeOrderNo, Long version) {
        // 账单状态修改支付成功
        orderService.paySuccess(tradeOrderNo, version);
    }
}
