package com.sun8min.order.provider;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun8min.order.api.OrderLineService;
import com.sun8min.order.entity.OrderLine;
import com.sun8min.order.mapper.OrderLineMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单商品项表 服务实现类
 * </p>
 *
 * @author sun8min
 * @since 2019-04-04
 */
@Service
public class OrderLineServiceImpl extends ServiceImpl<OrderLineMapper, OrderLine> implements OrderLineService {

}
