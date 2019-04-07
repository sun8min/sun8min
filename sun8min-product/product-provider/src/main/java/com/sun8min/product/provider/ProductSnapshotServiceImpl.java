package com.sun8min.product.provider;

import com.sun8min.product.entity.ProductSnapshot;
import com.sun8min.product.mapper.ProductSnapshotMapper;
import com.sun8min.product.api.ProductSnapshotService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.dubbo.config.annotation.Service;

/**
 * <p>
 * 商品快照表（记录价格、活动、上下架、删除变动） 服务实现类
 * </p>
 *
 * @author sun8min
 * @since 2019-04-06
 */
@Service(version = "${service.version}")
public class ProductSnapshotServiceImpl extends ServiceImpl<ProductSnapshotMapper, ProductSnapshot> implements ProductSnapshotService {

}
