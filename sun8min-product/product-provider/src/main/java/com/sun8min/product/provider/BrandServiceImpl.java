package com.sun8min.product.provider;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun8min.product.api.BrandService;
import com.sun8min.product.entity.Brand;
import com.sun8min.product.mapper.BrandMapper;
import org.apache.dubbo.config.annotation.Service;

/**
 * <p>
 * 品牌表 服务实现类
 * </p>
 *
 * @author sun8min
 * @since 2019-04-06
 */
@Service(version = "${service.version}")
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements BrandService {

}
