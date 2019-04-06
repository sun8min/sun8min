package com.sun8min.product.provider;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun8min.product.api.PropertyValueService;
import com.sun8min.product.entity.PropertyValue;
import com.sun8min.product.mapper.PropertyValueMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 属性value表 服务实现类
 * </p>
 *
 * @author sun8min
 * @since 2019-04-06
 */
@Service
public class PropertyValueServiceImpl extends ServiceImpl<PropertyValueMapper, PropertyValue> implements PropertyValueService {

}
