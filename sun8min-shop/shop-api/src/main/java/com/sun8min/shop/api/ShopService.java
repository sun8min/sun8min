package com.sun8min.shop.api;

import com.sun8min.shop.entity.Shop;

import java.util.List;

public interface ShopService {
    int deleteByPrimaryKey(Long shopId);

    int insert(Shop record);

    Shop selectByPrimaryKey(Long shopId);

    List<Shop> selectAll();

    int updateByPrimaryKey(Shop record);
}