package com.sun8min.account.api;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sun8min.account.entity.Account;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * <p>
 * 账户表 服务类
 * </p>
 *
 * @author sun8min
 * @since 2019-04-06
 */
public interface AccountService extends IService<Account> {

    /**
     * 根据用户id查找账户余额
     * @param userId
     * @return
     */
    BigDecimal findAmountByUserId(BigInteger userId);

}
