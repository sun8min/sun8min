package com.sun8min.product.provider;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sun8min.product.entity.Brand;
import com.sun8min.product.mapper.BrandMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 品牌表 服务实现类
 * </p>
 *
 * @author sun8min
 * @since 2019-04-04
 */
@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements IService<Brand> {

}
