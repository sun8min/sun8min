package com.sun8min.product.api;

import com.sun8min.product.entity.Shop;

import java.util.List;

public interface ShopService {
    int deleteByPrimaryKey(Long shopId);

    int insert(Shop record);

    Shop selectByPrimaryKey(Long shopId);

    List<Shop> selectAll();

    int updateByPrimaryKey(Shop record);
}