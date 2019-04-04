package com.sun8min.order.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

/**
 * <p>
 * 订单商品项表
 * </p>
 *
 * @author sun8min
 * @since 2019-04-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sun8min_order_line")
public class OrderLine extends Model<OrderLine> {

    private static final long serialVersionUID = 1L;

    /**
     * 订单商品项id
     */
    @TableId(value = "order_line_id", type = IdType.AUTO)
    private BigInteger orderLineId;

    /**
     * 商品id
     */
    @TableField("product_id")
    private BigInteger productId;

    /**
     * 商品价格（精确到分）
     */
    @TableField("product_price")
    private BigDecimal productPrice;

    /**
     * 商品数量
     */
    @TableField("product_quantity")
    private BigInteger productQuantity;

    /**
     * 主订单交易号
     */
    @TableField("trade_order_no")
    private String tradeOrderNo;

    /**
     * 扩展字段（json格式）
     */
    @TableField("extension_field")
    private String extensionField;

    /**
     * 版本号（用于乐观锁）
     */
    @TableField("version")
    @Version
    private Integer version;

    /**
     * 创建时间
     */
    @TableField("gmt_create")
    private LocalDateTime gmtCreate;

    /**
     * 修改时间
     */
    @TableField("gmt_modified")
    private LocalDateTime gmtModified;

    /**
     * 是否删除（0：否，1：是）
     */
    @TableField("is_deleted")
    @TableLogic
    private Boolean deleted;


    @Override
    protected Serializable pkVal() {
        return this.orderLineId;
    }

}
