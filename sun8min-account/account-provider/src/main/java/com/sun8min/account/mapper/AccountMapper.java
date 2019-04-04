package com.sun8min.account.mapper;

import com.sun8min.account.entity.Account;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.math.BigInteger;

/**
 * <p>
 * 账户表 Mapper 接口
 * </p>
 *
 * @author sun8min
 * @since 2019-04-04
 */
public interface AccountMapper extends BaseMapper<Account> {

    Account findByUserId(BigInteger userId);
}
