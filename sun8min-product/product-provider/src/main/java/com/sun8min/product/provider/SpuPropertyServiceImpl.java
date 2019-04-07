package com.sun8min.product.provider;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun8min.product.api.SpuPropertyService;
import com.sun8min.product.entity.SpuProperty;
import com.sun8min.product.mapper.SpuPropertyMapper;
import org.apache.dubbo.config.annotation.Service;

/**
 * <p>
 * spu-关键非关键属性关联表 服务实现类
 * </p>
 *
 * @author sun8min
 * @since 2019-04-06
 */
@Service(version = "${service.version}")
public class SpuPropertyServiceImpl extends ServiceImpl<SpuPropertyMapper, SpuProperty> implements SpuPropertyService {

}
