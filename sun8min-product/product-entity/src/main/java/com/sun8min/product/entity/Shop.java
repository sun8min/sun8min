package com.sun8min.product.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.math.BigInteger;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 商店表
 * </p>
 *
 * @author sun8min
 * @since 2019-04-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sun8min_shop")
public class Shop implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商店id
     */
    @TableId(value = "shop_id", type = IdType.AUTO)
    private BigInteger shopId;

    /**
     * 商店名
     */
    private String shopName;

    /**
     * 所属用户id
     */
    private BigInteger userId;

    /**
     * 扩展字段（json格式）
     */
    private String extensionField;

    /**
     * 版本号（用于乐观锁）
     */
    private Integer version;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    private LocalDateTime gmtModified;

    /**
     * 是否删除（0：否，1：是）
     */
    private Boolean deleted;


}
