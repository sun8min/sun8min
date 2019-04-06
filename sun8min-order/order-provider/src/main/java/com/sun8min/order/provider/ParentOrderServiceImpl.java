package com.sun8min.order.provider;

import com.sun8min.order.entity.ParentOrder;
import com.sun8min.order.mapper.ParentOrderMapper;
import com.sun8min.order.api.ParentOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 主订单表 服务实现类
 * </p>
 *
 * @author sun8min
 * @since 2019-04-06
 */
@Service
public class ParentOrderServiceImpl extends ServiceImpl<ParentOrderMapper, ParentOrder> implements ParentOrderService {

}
