package com.sun8min.user.provider;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun8min.user.api.CartService;
import com.sun8min.user.entity.Cart;
import com.sun8min.user.mapper.CartMapper;
import org.apache.dubbo.config.annotation.Service;

/**
 * <p>
 * 购物车表 服务实现类
 * </p>
 *
 * @author sun8min
 * @since 2019-04-06
 */
@Service(version = "${service.version}")
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements CartService {

}
