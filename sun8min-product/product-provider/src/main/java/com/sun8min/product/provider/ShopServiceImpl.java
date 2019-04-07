package com.sun8min.product.provider;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun8min.product.api.ShopService;
import com.sun8min.product.entity.Shop;
import com.sun8min.product.mapper.ShopMapper;
import org.apache.dubbo.config.annotation.Service;

/**
 * <p>
 * 商店表 服务实现类
 * </p>
 *
 * @author sun8min
 * @since 2019-04-06
 */
@Service(version = "${service.version}")
public class ShopServiceImpl extends ServiceImpl<ShopMapper, Shop> implements ShopService {

}
