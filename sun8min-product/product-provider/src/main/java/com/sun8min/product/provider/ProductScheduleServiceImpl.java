package com.sun8min.product.provider;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sun8min.product.entity.ProductSchedule;
import com.sun8min.product.mapper.ProductScheduleMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品上架定时表 服务实现类
 * </p>
 *
 * @author sun8min
 * @since 2019-04-04
 */
@Service
public class ProductScheduleServiceImpl extends ServiceImpl<ProductScheduleMapper, ProductSchedule> implements IService<ProductSchedule> {

}
