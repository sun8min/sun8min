package com.sun8min.seckill.repository;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayObject;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.sun8min.order.entity.Order;
import com.sun8min.order.entity.OrderLine;
import com.sun8min.product.api.ProductService;
import com.sun8min.product.api.ProductSnapshotService;
import com.sun8min.product.api.ShopService;
import com.sun8min.product.entity.Product;
import com.sun8min.product.entity.ProductSnapshot;
import com.sun8min.product.entity.Shop;
import com.sun8min.seckill.dto.PlaceOrderRequestDTO;
import com.sun8min.web.util.HttpUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 下单服务
 */
@Repository
public class SeckillOrderRepository {

    @Reference(version = "${service.version}")
    ShopService shopService;
    @Reference(version = "${service.version}")
    ProductService productService;
    @Reference(version = "${service.version}")
    ProductSnapshotService productSnapshotService;
    @Autowired
    HttpServletRequest httpServletRequest;

    /**
     * 构建下单请求
     *
     * @param userId            用户id
     * @param productSnapshotId 商品快照id
     * @return
     */
    public PlaceOrderRequestDTO buildQuestDTO(BigInteger userId, BigInteger productSnapshotId) {
        ProductSnapshot productSnapshot = productSnapshotService.getById(productSnapshotId);
        // 订单商品列表
        List<Pair<ProductSnapshot, Long>> productSnapshotQuantitiesList = new ArrayList<>();
        productSnapshotQuantitiesList.add(new ImmutablePair<>(productSnapshot, 1L));
        // 付款人
        BigInteger fromUserId = userId;
        // 购买的商店
        AtomicReference<Shop> shopAtomic = new AtomicReference<>();
        Optional.ofNullable(productSnapshot)
                .map(ProductSnapshot::getProductId)
                .ifPresent(
                        productId -> Optional.ofNullable(productService.getById(productId))
                                .map(Product::getShopId)
                                .ifPresent(
                                        shopId -> shopAtomic.set(shopService.getById(shopId))
                                )
                );
        return new PlaceOrderRequestDTO(fromUserId, shopAtomic.get(), productSnapshotQuantitiesList);
    }

    /**
     * 构建支付宝请求实体
     *
     * @param order
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public AlipayObject buildAlipayRequest(Order order) {
        // PC端 || 移动端
        AlipayObject bizmodel = HttpUtils.isPC(httpServletRequest) ? new AlipayTradePagePayModel() : new AlipayTradeWapPayModel();
        // 参数map
        Map<String, Object> paramsMap = new HashMap<>();
        // 商户订单号
        paramsMap.put("outTradeNo", order.getTradeOrderNo());
        // 订单总金额
        paramsMap.put("totalAmount", order.getOrderTradeAmount());
        // 订单标题
        String subject = "jiji";
        List<OrderLine> orderLines = order.getOrderLines();
        for (OrderLine orderLine : orderLines) {
            subject += " " + orderLine.getProductSnapshotId();
        }
        paramsMap.put("subject", subject);
        //销售产品码
        String productCode = HttpUtils.isPC(httpServletRequest) ? "FAST_INSTANT_TRADE_PAY" : "QUICK_WAP_WAY";
        paramsMap.put("productCode", productCode);
        // 额外参数
        Map<String, Object> passbackParamsMap = new HashMap<>();
        passbackParamsMap.put("version", order.getVersion());
        String passbackParams;
        try {
            passbackParams = URLEncoder.encode(JSON.toJSONString(passbackParamsMap), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException("额外参数使用URL编码异常");
        }
        paramsMap.put("passbackParams", passbackParams);

        // 实体转换
        try {
            BeanUtils.populate(bizmodel, paramsMap);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("实体转换异常");
        }
        return bizmodel;
    }

}
