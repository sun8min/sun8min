package com.sun8min.seckill.dto;

import com.sun8min.shop.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.List;

/**
 * 下单请求
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaceOrderRequestDTO {
    private long fromUserId;
    private long toUserId;
    private List<Pair<Product, Integer>> productQuantitiesList;
    private BigDecimal redpacketPayAmount;
}
