package com.sun8min.product.provider;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun8min.product.api.CategoryCategoryService;
import com.sun8min.product.entity.CategoryCategory;
import com.sun8min.product.mapper.CategoryCategoryMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 类目类目关联表 服务实现类
 * </p>
 *
 * @author sun8min
 * @since 2019-04-06
 */
@Service
public class CategoryCategoryServiceImpl extends ServiceImpl<CategoryCategoryMapper, CategoryCategory> implements CategoryCategoryService {

}
