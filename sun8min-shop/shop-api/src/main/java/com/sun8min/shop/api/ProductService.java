package com.sun8min.shop.api;

import com.sun8min.shop.entity.Product;

import java.util.List;

public interface ProductService {
    int deleteByPrimaryKey(Long productId);

    int insert(Product record);

    Product selectByPrimaryKey(Long productId);

    List<Product> selectAll();

    int updateByPrimaryKey(Product record);

    /**
     * 根据商店id查找商品列表
     * @param shopId
     * @return
     */
    List<Product> findByShopId(Long shopId);
}