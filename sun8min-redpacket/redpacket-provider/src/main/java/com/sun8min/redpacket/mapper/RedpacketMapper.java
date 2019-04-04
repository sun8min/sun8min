package com.sun8min.redpacket.mapper;

import com.sun8min.redpacket.entity.Redpacket;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.math.BigInteger;

/**
 * <p>
 * 用户红包表 Mapper 接口
 * </p>
 *
 * @author sun8min
 * @since 2019-04-04
 */
public interface RedpacketMapper extends BaseMapper<Redpacket> {

    Redpacket findByUserId(BigInteger userId);
}
