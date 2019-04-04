package com.sun8min.product.provider;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sun8min.product.entity.PropertyKey;
import com.sun8min.product.mapper.PropertyKeyMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 属性key表 服务实现类
 * </p>
 *
 * @author sun8min
 * @since 2019-04-04
 */
@Service
public class PropertyKeyServiceImpl extends ServiceImpl<PropertyKeyMapper, PropertyKey> implements IService<PropertyKey> {

}
