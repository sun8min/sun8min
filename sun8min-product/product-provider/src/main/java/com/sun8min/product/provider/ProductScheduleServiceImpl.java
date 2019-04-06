package com.sun8min.product.provider;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun8min.product.api.ProductScheduleService;
import com.sun8min.product.entity.ProductSchedule;
import com.sun8min.product.mapper.ProductScheduleMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品上架定时表 服务实现类
 * </p>
 *
 * @author sun8min
 * @since 2019-04-06
 */
@Service
public class ProductScheduleServiceImpl extends ServiceImpl<ProductScheduleMapper, ProductSchedule> implements ProductScheduleService {

}
