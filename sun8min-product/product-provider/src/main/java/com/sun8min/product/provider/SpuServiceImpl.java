package com.sun8min.product.provider;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun8min.product.api.SpuService;
import com.sun8min.product.entity.Spu;
import com.sun8min.product.mapper.SpuMapper;
import org.apache.dubbo.config.annotation.Service;

/**
 * <p>
 * spu表 服务实现类
 * </p>
 *
 * @author sun8min
 * @since 2019-04-06
 */
@Service(version = "${service.version}")
public class SpuServiceImpl extends ServiceImpl<SpuMapper, Spu> implements SpuService {

}
