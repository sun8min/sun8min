package com.sun8min.product.api;

import com.sun8min.product.entity.ProductSnapshot;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigInteger;

/**
 * <p>
 * 商品快照表（记录价格、活动、上下架、删除变动） 服务类
 * </p>
 *
 * @author sun8min
 * @since 2019-04-06
 */
public interface ProductSnapshotService extends IService<ProductSnapshot> {

    /**
     * 查找最后更新时间的商品快照
     *
     * @param productId 商品id
     * @return
     */
    ProductSnapshot findByProductId(BigInteger productId);
}
