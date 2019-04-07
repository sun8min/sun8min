package com.sun8min.product.provider;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun8min.product.api.SkuService;
import com.sun8min.product.entity.Sku;
import com.sun8min.product.mapper.SkuMapper;
import org.apache.dubbo.config.annotation.Service;

/**
 * <p>
 * sku表 服务实现类
 * </p>
 *
 * @author sun8min
 * @since 2019-04-06
 */
@Service(version = "${service.version}")
public class SkuServiceImpl extends ServiceImpl<SkuMapper, Sku> implements SkuService {

}
