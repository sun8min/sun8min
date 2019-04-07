package com.sun8min.seckill.service.impl;

import com.alibaba.fescar.core.context.RootContext;
import com.alibaba.fescar.spring.annotation.GlobalTransactional;
import com.sun8min.account.api.AccountTradeOrderService;
import com.sun8min.order.api.OrderService;
import com.sun8min.order.entity.Order;
import com.sun8min.order.entity.ParentOrder;
import com.sun8min.seckill.dto.PlaceOrderRequestDTO;
import com.sun8min.seckill.service.SeckillService;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class SeckillServiceImpl implements SeckillService {

    @Reference(version = "${service.version}")
    OrderService orderService;
    @Reference(version = "${service.version}")
    AccountTradeOrderService accountTradeOrderService;

    private boolean flag;

    @Override
    @GlobalTransactional(timeoutMills = 300000, name = "seckill-gts-fescar-example")
    public ParentOrder handleSeckill(PlaceOrderRequestDTO placeOrderRequestDTO) {
        // 分布式事务
        System.out.println("开始全局事务，XID = " + RootContext.getXID());
        // 1.下单
        Pair<ParentOrder, List<Order>> parentOrderListPair = orderService.placeOrder(
                placeOrderRequestDTO.getFromUserId(),
                placeOrderRequestDTO.getShopProductQuantitiesList()
        );
        // 2.账户交易
        Optional.ofNullable(parentOrderListPair)
                .map(Pair::getRight)
                .orElseGet(Collections::emptyList)
                .forEach(order -> accountTradeOrderService.trade(
                        order.getTradeOrderNo(),
                        order.getFromUserId(),
                        order.getToUserId(),
                        order.getOrderTradeAmount()));

        //打开注释测试事务发生异常后，全局回滚功能
//        if (!flag) {
//            throw new RuntimeException("测试抛异常后，分布式事务回滚！");
//        }
        return Optional.ofNullable(parentOrderListPair).map(Pair::getLeft).orElse(null);
    }
}
