package com.sun8min.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sun8min.order.entity.Order;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 订单表 Mapper 接口
 * </p>
 *
 * @author sun8min
 * @since 2019-04-06
 */
public interface OrderMapper extends BaseMapper<Order> {

    Order findByTradeOrderNo(String tradeOrderNo);

    Integer paySuccess(@Param("tradeOrderNo") String tradeOrderNo, @Param("version") Long version, @Param("orderStatus") Integer orderStatus);
}
