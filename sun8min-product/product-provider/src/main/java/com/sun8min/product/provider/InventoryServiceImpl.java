package com.sun8min.product.provider;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun8min.product.api.InventoryService;
import com.sun8min.product.entity.Inventory;
import com.sun8min.product.mapper.InventoryMapper;
import org.apache.dubbo.config.annotation.Service;

/**
 * <p>
 * 库存表 服务实现类
 * </p>
 *
 * @author sun8min
 * @since 2019-04-06
 */
@Service(version = "${service.version}")
public class InventoryServiceImpl extends ServiceImpl<InventoryMapper, Inventory> implements InventoryService {

}
