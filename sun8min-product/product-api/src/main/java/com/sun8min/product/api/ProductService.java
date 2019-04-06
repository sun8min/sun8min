package com.sun8min.product.api;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sun8min.product.entity.Product;

import java.math.BigInteger;
import java.util.List;

/**
 * <p>
 * 商品表 服务类
 * </p>
 *
 * @author sun8min
 * @since 2019-04-06
 */
public interface ProductService extends IService<Product> {

    /**
     * 根据shopId查找商品
     * @param shopId 商店id
     * @return 商品列表
     */
    List<Product> findByShopId(BigInteger shopId);
}
