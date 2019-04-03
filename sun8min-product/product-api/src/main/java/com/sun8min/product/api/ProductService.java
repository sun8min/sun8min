package com.sun8min.product.api;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sun8min.product.entity.Product;

import java.util.List;

public interface ProductService  extends IService<Product> {

    List<Product> selectAll();

    /**
     * 根据商店id查找商品列表
     * @param shopId
     * @return
     */
    List<Product> findByShopId(Long shopId);
}