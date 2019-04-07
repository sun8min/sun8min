package com.sun8min.seckill.repository;

import com.sun8min.product.api.ProductService;
import com.sun8min.product.api.ShopService;
import com.sun8min.product.entity.Product;
import com.sun8min.product.entity.Shop;
import com.sun8min.seckill.dto.PlaceOrderRequestDTO;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 下单服务
 */
@Repository
public class PlaceOrderRepository {

    @Reference(version = "${service.version}")
    ShopService shopService;
    @Reference(version = "${service.version}")
    ProductService productService;

    /**
     * 构建下单请求
     *
     * @param userId                用户id
     * @param productId             商品id
     * @return
     */
    public PlaceOrderRequestDTO buildQuestDTO(BigInteger userId, BigInteger productId) {
        Product product = productService.getById(productId);
        // 订单商品列表
        List<Pair<Product, Long>> productQuantitiesList = new ArrayList<>();
        productQuantitiesList.add(new ImmutablePair<>(product, 1L));
        // 付款人
        BigInteger fromUserId = userId;

        // 对product根据商家进行拆分
        Map<Shop, List<Pair<Product, Long>>> shopProductQuantitiesList = productQuantitiesList
                .parallelStream()
                .collect(
                        Collectors.groupingBy(
                                pair -> {
                                    BigInteger shopId = Optional.ofNullable(pair)
                                            .map(Pair::getLeft)
                                            .map(Product::getShopId)
                                            .orElseThrow(() -> new RuntimeException("商品拆分失败"));
                                    return shopService.getById(shopId);
                                }
                        )
                );
        return new PlaceOrderRequestDTO(fromUserId, shopProductQuantitiesList);
    }

}
