package com.sun8min.account.api;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sun8min.account.entity.AccountTradeOrder;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * <p>
 * 账户交易表 服务类
 * </p>
 *
 * @author sun8min
 * @since 2019-04-04
 */
public interface AccountTradeOrderService extends IService<AccountTradeOrder> {


    /**
     * 账户交易
     * @param tradeOrderNo 交易订单
     * @param fromUserId 转出账户
     * @param toUserId 转入账户
     * @param accountTradeAmount 账户交易额
     * @return 交易结果
     */
    Boolean trade(String tradeOrderNo, BigInteger fromUserId, BigInteger toUserId, BigDecimal accountTradeAmount);
}
