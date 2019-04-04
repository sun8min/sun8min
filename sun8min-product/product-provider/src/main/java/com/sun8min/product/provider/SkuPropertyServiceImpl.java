package com.sun8min.product.provider;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sun8min.product.entity.SkuProperty;
import com.sun8min.product.mapper.SkuPropertyMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * sku-销售属性关联表 服务实现类
 * </p>
 *
 * @author sun8min
 * @since 2019-04-04
 */
@Service
public class SkuPropertyServiceImpl extends ServiceImpl<SkuPropertyMapper, SkuProperty> implements IService<SkuProperty> {

}
