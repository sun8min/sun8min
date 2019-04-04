package com.sun8min.redpacket.provider;

import com.sun8min.redpacket.entity.Redpacket;
import com.sun8min.redpacket.mapper.RedpacketMapper;
import com.sun8min.redpacket.api.RedpacketService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * <p>
 * 用户红包表 服务实现类
 * </p>
 *
 * @author sun8min
 * @since 2019-04-04
 */
@Service
public class RedpacketServiceImpl extends ServiceImpl<RedpacketMapper, Redpacket> implements RedpacketService {

    @Override
    public BigDecimal findAmountByUserId(BigInteger userId) {
        return baseMapper.findByUserId(userId).getRedpacketAmount();
    }
}
