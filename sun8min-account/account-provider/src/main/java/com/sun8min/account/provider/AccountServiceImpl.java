package com.sun8min.account.provider;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun8min.account.api.AccountService;
import com.sun8min.account.entity.Account;
import com.sun8min.account.mapper.AccountMapper;
import org.apache.dubbo.config.annotation.Service;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * <p>
 * 账户表 服务实现类
 * </p>
 *
 * @author sun8min
 * @since 2019-04-06
 */
@Service(version = "${service.version}")
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {

    @Override
    public BigDecimal findAmountByUserId(BigInteger userId) {
        return baseMapper.findByUserId(userId).getAccountAmount();
    }
}
