package com.sun8min.seckill.service;

import com.google.common.base.Strings;
import com.sun8min.seckill.vo.PlaceOrderRequest;
import com.sun8min.shop.api.ProductService;
import com.sun8min.shop.api.ShopService;
import com.sun8min.shop.entity.Product;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PlaceOrderRepository {

    @Reference(version = "${service.version}")
    ShopService shopService;
    @Reference(version = "${service.version}")
    ProductService productService;

    public PlaceOrderRequest buildQuest(Long productId, String redpacketPayAmountStr, long userId) {
        Product product = productService.selectByPrimaryKey(productId);
        BigDecimal redpacketPayAmount = Strings.isNullOrEmpty(redpacketPayAmountStr) ? BigDecimal.ZERO : new BigDecimal(redpacketPayAmountStr);
        // 参数校验
        checkParam(product, userId, redpacketPayAmount);
        // 订单商品列表
        List<Pair<Product, Integer>> productQuantitiesList = new ArrayList<>();
        productQuantitiesList.add(new ImmutablePair<>(product, 1));
        // 付款人
        long fromUserId = userId;
        // 收款人
        long toUserId = shopService.selectByPrimaryKey(product.getShopId()).getUserId();
        return new PlaceOrderRequest(fromUserId, toUserId, productQuantitiesList, redpacketPayAmount);
    }

    private static void checkParam(Product product, long payerUserId, BigDecimal redpacketPayAmount) {
        // 商品价格
        BigDecimal productPrice = product.getProductPrice();
        // 校验合法性
        boolean smallThanZero = redpacketPayAmount.compareTo(BigDecimal.ZERO) < 0;
        boolean bigThanProductPrice = redpacketPayAmount.compareTo(productPrice) > 0;
        if (smallThanZero) throw new InvalidParameterException("红包金额不能小于0");
        if (bigThanProductPrice) throw new InvalidParameterException("红包金额不能大于商品价格");
    }
}
