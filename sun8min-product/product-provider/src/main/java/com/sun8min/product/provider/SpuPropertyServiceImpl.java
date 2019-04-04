package com.sun8min.product.provider;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sun8min.product.entity.SpuProperty;
import com.sun8min.product.mapper.SpuPropertyMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * spu-关键非关键属性关联表 服务实现类
 * </p>
 *
 * @author sun8min
 * @since 2019-04-04
 */
@Service
public class SpuPropertyServiceImpl extends ServiceImpl<SpuPropertyMapper, SpuProperty> implements IService<SpuProperty> {

}
