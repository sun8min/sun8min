package com.sun8min.seckill.dto;

import com.sun8min.product.entity.Product;
import com.sun8min.product.entity.Shop;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * 下单请求
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaceOrderRequestDTO {
    private BigInteger fromUserId;
    private Map<Shop, List<Pair<Product, Long>>> shopProductQuantitiesList;
}
