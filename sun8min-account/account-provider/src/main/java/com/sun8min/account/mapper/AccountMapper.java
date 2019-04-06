package com.sun8min.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sun8min.account.entity.Account;

import java.math.BigInteger;

/**
 * <p>
 * 账户表 Mapper 接口
 * </p>
 *
 * @author sun8min
 * @since 2019-04-06
 */
public interface AccountMapper extends BaseMapper<Account> {

    Account findByUserId(BigInteger userId);
}
