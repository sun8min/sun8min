package com.sun8min.product.provider;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sun8min.product.entity.ProductProduct;
import com.sun8min.product.mapper.ProductProductMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品商品关联表 服务实现类
 * </p>
 *
 * @author sun8min
 * @since 2019-04-04
 */
@Service
public class ProductProductServiceImpl extends ServiceImpl<ProductProductMapper, ProductProduct> implements IService<ProductProduct> {

}
