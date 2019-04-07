package com.sun8min.product.provider;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun8min.product.api.CategoryService;
import com.sun8min.product.entity.Category;
import com.sun8min.product.mapper.CategoryMapper;
import org.apache.dubbo.config.annotation.Service;

/**
 * <p>
 * 类目表 服务实现类
 * </p>
 *
 * @author sun8min
 * @since 2019-04-06
 */
@Service(version = "${service.version}")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

}
