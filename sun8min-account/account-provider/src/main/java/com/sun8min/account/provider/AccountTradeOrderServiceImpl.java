package com.sun8min.account.provider;

import com.alibaba.fescar.core.context.RootContext;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun8min.account.api.AccountTradeOrderService;
import com.sun8min.account.entity.Account;
import com.sun8min.account.entity.AccountTradeOrder;
import com.sun8min.account.mapper.AccountMapper;
import com.sun8min.account.mapper.AccountTradeOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * <p>
 * 账户交易表 服务实现类
 * </p>
 *
 * @author sun8min
 * @since 2019-04-06
 */
@Service(version = "${service.version}")
public class AccountTradeOrderServiceImpl extends ServiceImpl<AccountTradeOrderMapper, AccountTradeOrder> implements AccountTradeOrderService {

    @Autowired
    AccountTradeOrderMapper accountTradeOrderMapper;
    @Autowired
    AccountMapper accountMapper;

    @Transactional
    @Override
    public Boolean trade(String tradeOrderNo,
                         BigInteger fromUserId,
                         BigInteger toUserId,
                         BigDecimal accountTradeAmount) {
        System.out.println("全局事务id ：" + RootContext.getXID());
        // 1. 交易转出记录
        accountTradeOrderMapper.insert(new AccountTradeOrder()
                .setAccountTradeAmount(accountTradeAmount)
                .setTradeOrderNo(tradeOrderNo)
                .setUserId(fromUserId)
                .setTradeType(AccountTradeOrder.TradeType.TRANSFER_OUT.getCode()));
        // 2. 交易转入记录
        accountTradeOrderMapper.insert(new AccountTradeOrder()
                .setAccountTradeAmount(accountTradeAmount)
                .setTradeOrderNo(tradeOrderNo)
                .setUserId(toUserId)
                .setTradeType(AccountTradeOrder.TradeType.TRANSFER_IN.getCode()));
        // 3. 账户转出
        Account fromAccount = accountMapper.findByUserId(fromUserId);
        fromAccount.setAccountAmount(fromAccount.getAccountAmount().subtract(accountTradeAmount));
        accountMapper.updateById(fromAccount);
        // 4. 账户转入
        Account toAccount = accountMapper.findByUserId(toUserId);
        toAccount.setAccountAmount(toAccount.getAccountAmount().add(accountTradeAmount));
        accountMapper.updateById(toAccount);
        return true;
    }
}
