package com.sun8min.account.provider;

import com.alibaba.fescar.core.context.RootContext;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun8min.account.api.AccountTradeOrderService;
import com.sun8min.account.entity.Account;
import com.sun8min.account.entity.AccountTradeOrder;
import com.sun8min.account.mapper.AccountMapper;
import com.sun8min.account.mapper.AccountTradeOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * <p>
 * 账户交易表 服务实现类
 * </p>
 *
 * @author sun8min
 * @since 2019-04-04
 */
@Service
public class AccountTradeOrderServiceImpl extends ServiceImpl<AccountTradeOrderMapper, AccountTradeOrder> implements AccountTradeOrderService {

    @Autowired
    AccountTradeOrderMapper accountTradeOrderMapper;
    @Autowired
    AccountMapper accountMapper;

    @Transactional
    @Override
    public Boolean trade(String tradeOrderNo, BigInteger fromUserId, BigInteger toUserId, BigDecimal accountTradeAmount) {
        System.out.println("全局事务id ：" + RootContext.getXID());
        // 账户交易记录
        AccountTradeOrder accountTradeOrder = new AccountTradeOrder();
        accountTradeOrder.setFromUserId(fromUserId);
        accountTradeOrder.setToUserId(fromUserId);
        accountTradeOrder.setAccountTradeAmount(accountTradeAmount);
        accountTradeOrder.setTradeOrderNo(tradeOrderNo);
        accountTradeOrderMapper.insert(accountTradeOrder);
        // 账户转出
        Account fromAccount = accountMapper.findByUserId(fromUserId);
        fromAccount.setAccountAmount(fromAccount.getAccountAmount().subtract(accountTradeAmount));
        accountMapper.updateById(fromAccount);
        // 账户转入
        Account toAccount = accountMapper.findByUserId(toUserId);
        toAccount.setAccountAmount(toAccount.getAccountAmount().add(accountTradeAmount));
        accountMapper.updateById(toAccount);
        return true;
    }
}
