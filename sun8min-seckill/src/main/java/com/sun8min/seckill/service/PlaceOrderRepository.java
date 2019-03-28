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

/**
 * 下单服务
 */
@Service
public class PlaceOrderRepository {

    @Reference(version = "${service.version}")
    ShopService shopService;
    @Reference(version = "${service.version}")
    ProductService productService;

    /**
     * 构建下单请求
     * @param userId 用户id
     * @param productId 商品id
     * @param redpacketPayAmountStr 所输入的红包支付金额字符串
     * @return
     */
    public PlaceOrderRequest buildQuest(long userId, Long productId, String redpacketPayAmountStr) {
        Product product = productService.selectByPrimaryKey(productId);
        BigDecimal redpacketPayAmount = Strings.isNullOrEmpty(redpacketPayAmountStr) ? BigDecimal.ZERO : new BigDecimal(redpacketPayAmountStr);
        // 参数校验
        checkParam(product, redpacketPayAmount);
        // 订单商品列表
        List<Pair<Product, Integer>> productQuantitiesList = new ArrayList<>();
        productQuantitiesList.add(new ImmutablePair<>(product, 1));
        // 付款人
        long fromUserId = userId;
        // 收款人
        long toUserId = shopService.selectByPrimaryKey(product.getShopId()).getUserId();
        return new PlaceOrderRequest(fromUserId, toUserId, productQuantitiesList, redpacketPayAmount);
    }

    /**
     * 参数校验
     * @param product 商品
     * @param redpacketPayAmount 用户输入的红包交易额
     */
    private static void checkParam(Product product, BigDecimal redpacketPayAmount) {
        // 商品价格
        BigDecimal productPrice = product.getProductPrice();
        // 非负数
        boolean bigOrEqualZero = redpacketPayAmount.compareTo(BigDecimal.ZERO) >= 0;
        // 精度位数<=2（最多到分，即小数点后2位）
        boolean scaleSmallOrEqual = redpacketPayAmount.scale() <= 2;
        // 不超过商品价格
        boolean smallThanProductPrice = redpacketPayAmount.compareTo(productPrice) <= 0;
        if (!bigOrEqualZero) throw new InvalidParameterException("红包金额不能小于0");
        if (!scaleSmallOrEqual) throw new InvalidParameterException("红包金额只能到分");
        if (!smallThanProductPrice) throw new InvalidParameterException("红包金额不能大于商品价格");
    }
}
