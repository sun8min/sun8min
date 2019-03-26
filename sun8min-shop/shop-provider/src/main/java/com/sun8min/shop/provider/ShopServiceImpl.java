package com.sun8min.shop.provider;

import com.sun8min.shop.api.ShopService;
import com.sun8min.shop.dao.ShopDao;
import com.sun8min.shop.entity.Shop;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service(version = "${service.version}")
public class ShopServiceImpl implements ShopService {

    @Autowired
    ShopDao shopDao;

    public int deleteByPrimaryKey(Long ShopId) {
        return shopDao.deleteByPrimaryKey(ShopId);
    }

    public int insert(Shop record) {
        return shopDao.insert(record);
    }

    public Shop selectByPrimaryKey(Long ShopId) {
        return shopDao.selectByPrimaryKey(ShopId);
    }

    public List<Shop> selectAll() {
        return shopDao.selectAll();
    }

    public int updateByPrimaryKey(Shop record) {
        return shopDao.updateByPrimaryKey(record);
    }
}
