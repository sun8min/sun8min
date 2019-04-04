package com.sun8min.account.provider;

import com.sun8min.account.entity.Account;
import com.sun8min.account.mapper.AccountMapper;
import com.sun8min.account.api.AccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * <p>
 * 账户表 服务实现类
 * </p>
 *
 * @author sun8min
 * @since 2019-04-04
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {

    @Override
    public BigDecimal findAmountByUserId(Long userId) {
        return baseMapper.findByUserId(userId).getAccountAmount();
    }
}
