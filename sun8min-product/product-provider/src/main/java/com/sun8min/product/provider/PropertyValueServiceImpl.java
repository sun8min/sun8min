package com.sun8min.product.provider;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sun8min.product.entity.PropertyValue;
import com.sun8min.product.mapper.PropertyValueMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 属性value表 服务实现类
 * </p>
 *
 * @author sun8min
 * @since 2019-04-04
 */
@Service
public class PropertyValueServiceImpl extends ServiceImpl<PropertyValueMapper, PropertyValue> implements IService<PropertyValue> {

}
