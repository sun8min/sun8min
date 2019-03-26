package com.sun8min.shop.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品表
 *
 * @author sun8min
 * @date 2019-03-27 00:49:21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product implements Serializable {
    /**
     * 商品id
     */
    private Long productId;

    /**
     * 商品名
     */
    private String productName;

    /**
     * 商品图片url
     */
    private String productImage;

    /**
     * 商品售价（精确到万分之一）
     */
    private Long productPrice;

    /**
     * 商品折扣类型（0：无折扣，1：输入折后价，2：输入折扣百分比）
     */
    private Integer productDiscountType;

    /**
     * 商品折后价（精确到万分之一）
     */
    private Long productDiscountPrice;

    /**
     * 商品折扣百分比
     */
    private Integer productDiscountPercent;

    /**
     * 所属商店id
     */
    private Long productShopId;

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