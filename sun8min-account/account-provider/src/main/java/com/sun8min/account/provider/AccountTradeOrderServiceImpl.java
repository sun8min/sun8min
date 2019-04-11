package com.sun8min.account.provider;

import com.alibaba.fescar.core.context.RootContext;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun8min.account.api.AccountTradeOrderService;
import com.sun8min.account.entity.Account;
import com.sun8min.account.entity.AccountTradeOrder;
import com.sun8min.account.mapper.AccountMapper;
import com.sun8min.account.mapper.AccountTradeOrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.UUID;

/**
 * <p>
 * 账户交易表 服务实现类
 * </p>
 *
 * @author sun8min
 * @since 2019-04-12
 */
@Service(version = "${service.version}")
@Slf4j
public class AccountTradeOrderServiceImpl extends ServiceImpl<AccountTradeOrderMapper, AccountTradeOrder> implements AccountTradeOrderService {

    @Autowired
    AccountTradeOrderMapper accountTradeOrderMapper;
    @Autowired
    AccountMapper accountMapper;

    @Transactional
    @Override
    public AccountTradeOrder trade(String tradeOrderNo,
                         BigInteger fromUserId,
                         BigInteger toUserId,
                         BigDecimal accountTradeAmount) {
        log.info("全局事务id ：" + RootContext.getXID());
        String accountTradeOrderNo = UUID.randomUUID().toString().replace("-", "");
        // 1. 交易记录
        AccountTradeOrder accountTradeOrder = new AccountTradeOrder()
                .setAccountTradeAmount(accountTradeAmount)
                .setAccountTradeOrderNo(accountTradeOrderNo)
                .setTradeOrderNo(tradeOrderNo)
                .setFromUserId(fromUserId)
                .setToUserId(toUserId);
        accountTradeOrderMapper.insert(accountTradeOrder);
        // 2. 账户转出
        Account fromAccount = accountMapper.findByUserId(fromUserId);
        fromAccount.setAccountAmount(fromAccount.getAccountAmount().subtract(accountTradeAmount));
        accountMapper.updateById(fromAccount);
        // 3. 账户转入
        Account toAccount = accountMapper.findByUserId(toUserId);
        toAccount.setAccountAmount(toAccount.getAccountAmount().add(accountTradeAmount));
        accountMapper.updateById(toAccount);
        return accountTradeOrderMapper.selectById(accountTradeOrder.getAccountTradeOrderId());
    }
}
