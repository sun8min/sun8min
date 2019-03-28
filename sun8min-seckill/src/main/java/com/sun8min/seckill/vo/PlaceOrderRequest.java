package com.sun8min.seckill.vo;

import com.sun8min.shop.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaceOrderRequest {
    private long fromUserId;
    private long toUserId;
    private List<Pair<Product, Integer>> productQuantitiesList;
    private BigDecimal redpacketPayAmount;
}
