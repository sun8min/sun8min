package com.sun8min.product.provider;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sun8min.product.entity.Sku;
import com.sun8min.product.mapper.SkuMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * sku表 服务实现类
 * </p>
 *
 * @author sun8min
 * @since 2019-04-04
 */
@Service
public class SkuServiceImpl extends ServiceImpl<SkuMapper, Sku> implements IService<Sku> {

}
