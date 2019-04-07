package com.sun8min.product.provider;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun8min.product.api.ProductService;
import com.sun8min.product.entity.Product;
import com.sun8min.product.mapper.ProductMapper;
import org.apache.dubbo.config.annotation.Service;

import java.math.BigInteger;
import java.util.List;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author sun8min
 * @since 2019-04-06
 */
@Service(version = "${service.version}")
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Override
    public List<Product> findByShopId(BigInteger shopId) {
        return baseMapper.findListByShopId(shopId);
    }
}
