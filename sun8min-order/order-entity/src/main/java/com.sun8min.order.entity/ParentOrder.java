package com.sun8min.order.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;

/**
 * <p>
 * 主订单表
 * </p>
 *
 * @author sun8min
 * @since 2019-04-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sun8min_parent_order")
public class ParentOrder extends Model<ParentOrder> {

    private static final long serialVersionUID = 1L;

    /**
     * 主订单id
     */
    @TableId(value = "parent_order_id", type = IdType.AUTO)
    private BigInteger parentOrderId;

    /**
     * 主订单号
     */
    @TableField("parent_order_no")
    private String parentOrderNo;

    /**
     * 资金转出用户id
     */
    @TableField("from_user_id")
    private BigInteger fromUserId;

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
    private Long version;

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
        return this.parentOrderId;
    }

}
