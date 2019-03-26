package com.sun8min.shop.dao;

import com.sun8min.shop.entity.Product;
import java.util.List;

public interface ProductDao {
    int deleteByPrimaryKey(Long productId);

    int insert(Product record);

    Product selectByPrimaryKey(Long productId);

    List<Product> selectAll();

    int updateByPrimaryKey(Product record);
}