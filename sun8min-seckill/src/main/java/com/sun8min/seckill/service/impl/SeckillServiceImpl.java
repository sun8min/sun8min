package com.sun8min.seckill.service.impl;

import com.alibaba.fescar.core.context.RootContext;
import com.alibaba.fescar.spring.annotation.GlobalTransactional;
import com.sun8min.capital.api.CapitalTradeOrderService;
import com.sun8min.order.api.OrderService;
import com.sun8min.order.entity.Order;
import com.sun8min.redpacket.api.RedpacketTradeOrderService;
import com.sun8min.seckill.dto.PlaceOrderRequestDTO;
import com.sun8min.seckill.service.SeckillService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class SeckillServiceImpl implements SeckillService {

    @Reference(version = "${service.version}")
    OrderService orderService;
    @Reference(version = "${service.version}")
    RedpacketTradeOrderService redpacketTradeOrderService;
    @Reference(version = "${service.version}")
    CapitalTradeOrderService capitalTradeOrderService;

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
                placeOrderRequestDTO.getProductQuantitiesList(),
                placeOrderRequestDTO.getRedpacketPayAmount()
        );
        // 2.红包交易
        // 如果交易金额<=0，则不记录
        if (order.getRedpacketTradeAmount().compareTo(BigDecimal.ZERO) > 0) {
            redpacketTradeOrderService.trade(
                    order.getTradeOrderNo(),
                    placeOrderRequestDTO.getFromUserId(),
                    placeOrderRequestDTO.getToUserId(),
                    order.getRedpacketTradeAmount()
            );
        }
        // 3.账户交易
        // 如果交易金额<=0，则不记录
        if (order.getCapitalTradeAmount().compareTo(BigDecimal.ZERO) > 0) {
            capitalTradeOrderService.trade(
                    order.getTradeOrderNo(),
                    placeOrderRequestDTO.getFromUserId(),
                    placeOrderRequestDTO.getToUserId(),
                    order.getCapitalTradeAmount()
            );
        }
        //打开注释测试事务发生异常后，全局回滚功能
//        if (!flag) {
//            throw new RuntimeException("测试抛异常后，分布式事务回滚！");
//        }
        return order;
    }
}
