package com.sun8min.order.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 订单商品项表
 *
 * @author sun8min
 * @date 2019-03-28 13:22:51
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderLine implements Serializable {
    /**
     * 订单商品项id
     */
    private Long orderLineId;

    /**
     * 商品id
     */
    private Long productId;

    /**
     * 商品价格（精确到分）
     */
    private BigDecimal productPrice;

    /**
     * 商品数量
     */
    private Long productQuantity;

    /**
     * 主订单交易号
     */
    private String tradeOrderNo;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;

    /**
     * 是否删除（0:正常，1:已删）
     */
    private Integer isDeleted;

    private static final long serialVersionUID = 1L;
}