package com.sun8min.order.provider.vo;

import com.google.common.base.Strings;
import com.sun8min.shop.entity.Product;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class PlaceOrderRequest {
    private long productId;
    private String redpacketPayAmountStr;
    private long userId;
    private BigDecimal redpacketPayAmount;
    private List<Pair<Product, Integer>> productQuantitiesList;
    private long fromUserId;
    private long toUserId;

    public BigDecimal getRedpacketPayAmount() {
        return redpacketPayAmount;
    }

    public List<Pair<Product, Integer>> getProductQuantitiesList() {
        return productQuantitiesList;
    }

    public long getFromUserId() {
        return fromUserId;
    }

    public long getToUserId() {
        return toUserId;
    }

    public PlaceOrderRequest build() {
        Product product = productService.selectByPrimaryKey(productId);
        redpacketPayAmount = Strings.isNullOrEmpty(redpacketPayAmountStr) ? BigDecimal.ZERO : new BigDecimal(redpacketPayAmountStr);
        // 参数校验
        checkParam(product, userId, redpacketPayAmount);
        // 订单商品列表
        productQuantitiesList = new ArrayList<>();
        productQuantitiesList.add(new ImmutablePair<>(product, 1));
        // 付款人
        fromUserId = userId;
        // 收款人
        toUserId = shopService.selectByPrimaryKey(product.getShopId()).getUserId();
        return this;
    }

    private void checkParam(Product product, long payerUserId, BigDecimal redpacketPayAmount) {
        // 商品价格
        BigDecimal productPrice = product.getProductPrice();
        // 校验合法性
        boolean smallThanZero = redpacketPayAmount.compareTo(BigDecimal.ZERO) < 0;
        boolean bigThanProductPrice = redpacketPayAmount.compareTo(productPrice) > 0;
        if (smallThanZero) throw new InvalidParameterException("红包金额不能小于0");
        if (bigThanProductPrice) throw new InvalidParameterException("红包金额不能大于商品价格");
    }
}
