package com.sun8min.product.provider;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun8min.product.api.PropertyKeyService;
import com.sun8min.product.entity.PropertyKey;
import com.sun8min.product.mapper.PropertyKeyMapper;
import org.apache.dubbo.config.annotation.Service;

/**
 * <p>
 * 属性key表 服务实现类
 * </p>
 *
 * @author sun8min
 * @since 2019-04-06
 */
@Service(version = "${service.version}")
public class PropertyKeyServiceImpl extends ServiceImpl<PropertyKeyMapper, PropertyKey> implements PropertyKeyService {

}
