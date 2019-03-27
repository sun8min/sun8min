package com.sun8min.shop.provider;

import com.sun8min.shop.api.ProductService;
import com.sun8min.shop.dao.ProductDao;
import com.sun8min.shop.entity.Product;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service(version = "${service.version}")
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductDao productDao;

    public int deleteByPrimaryKey(Long productId) {
        return productDao.deleteByPrimaryKey(productId);
    }

    public int insert(Product record) {
        return productDao.insert(record);
    }

    public Product selectByPrimaryKey(Long productId) {
        return productDao.selectByPrimaryKey(productId);
    }

    public List<Product> selectAll() {
        return productDao.selectAll();
    }

    public int updateByPrimaryKey(Product record) {
        return productDao.updateByPrimaryKey(record);
    }

    public List<Product> findByShopId(Long shopId) {
        return productDao.findByShopId(shopId);
    }
}
