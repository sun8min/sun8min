package com.sun8min.seckill.service.impl;

import com.alibaba.fescar.core.context.RootContext;
import com.alibaba.fescar.spring.annotation.GlobalTransactional;
import com.sun8min.account.api.AccountTradeOrderService;
import com.sun8min.order.api.OrderService;
import com.sun8min.order.entity.Order;
import com.sun8min.seckill.dto.PlaceOrderRequestDTO;
import com.sun8min.seckill.service.SeckillService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

@Service
public class SeckillServiceImpl implements SeckillService {

    @Reference(version = "${service.version}")
    OrderService orderService;
    @Reference(version = "${service.version}")
    AccountTradeOrderService accountTradeOrderService;

    private boolean flag;

    @Override
    @GlobalTransactional(timeoutMills = 300000, name = "seckill-gts-fescar-example")
    public Order handleSeckill(PlaceOrderRequestDTO placeOrderRequestDTO) {
        // TODO 分布式事务
        System.out.println("开始全局事务，XID = " + RootContext.getXID());
        // 1.下单
        Order order = orderService.placeOrder(
                placeOrderRequestDTO.getFromUserId(),
                placeOrderRequestDTO.getToUserId(),
                placeOrderRequestDTO.getProductQuantitiesList()
        );
        // 2.账户交易
        accountTradeOrderService.trade(
                order.getTradeOrderNo(),
                placeOrderRequestDTO.getFromUserId(),
                placeOrderRequestDTO.getToUserId(),
                order.getOrderTradeAmount());
        //打开注释测试事务发生异常后，全局回滚功能
//        if (!flag) {
//            throw new RuntimeException("测试抛异常后，分布式事务回滚！");
//        }
        return order;
    }
}
