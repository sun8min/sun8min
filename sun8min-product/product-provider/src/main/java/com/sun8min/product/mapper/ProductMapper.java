package com.sun8min.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sun8min.product.entity.Product;

import java.math.BigInteger;
import java.util.List;

/**
 * <p>
 * 商品表 Mapper 接口
 * </p>
 *
 * @author sun8min
 * @since 2019-04-06
 */
public interface ProductMapper extends BaseMapper<Product> {

    List<Product> findListByShopId(BigInteger shopId);
}
