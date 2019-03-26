package com.sun8min.shop.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商店表
 *
 * @author sun8min
 * @date 2019-03-27 00:49:21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Shop implements Serializable {
    /**
     * 商店id
     */
    private Long shopId;

    /**
     * 商店名
     */
    private String shopName;

    /**
     * 所属用户id
     */
    private Long userId;

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