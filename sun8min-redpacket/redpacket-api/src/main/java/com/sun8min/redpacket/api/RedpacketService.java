package com.sun8min.redpacket.api;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sun8min.redpacket.entity.Redpacket;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * <p>
 * 用户红包表 服务类
 * </p>
 *
 * @author sun8min
 * @since 2019-04-04
 */
public interface RedpacketService extends IService<Redpacket> {

    /**
     * 根据用户id查找红包余额
     * @param userId
     * @return
     */
    BigDecimal findAmountByUserId(BigInteger userId);
}
