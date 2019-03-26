package com.sun8min.order.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单商品项表
 *
 * @author sun8min
 * @date 2019-03-27 01:12:51
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
     * 商品价格（精确到万分之一）
     */
    private Long productPrice;

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