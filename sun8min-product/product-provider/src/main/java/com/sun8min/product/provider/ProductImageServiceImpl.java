package com.sun8min.product.provider;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun8min.product.api.ProductImageService;
import com.sun8min.product.entity.ProductImage;
import com.sun8min.product.mapper.ProductImageMapper;
import org.apache.dubbo.config.annotation.Service;

/**
 * <p>
 * 商品图片表 服务实现类
 * </p>
 *
 * @author sun8min
 * @since 2019-04-06
 */
@Service(version = "${service.version}")
public class ProductImageServiceImpl extends ServiceImpl<ProductImageMapper, ProductImage> implements ProductImageService {

}
