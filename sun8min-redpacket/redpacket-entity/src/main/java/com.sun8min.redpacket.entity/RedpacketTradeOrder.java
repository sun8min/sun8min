package com.sun8min.redpacket.entity;

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
 * 用户红包交易表
 * </p>
 *
 * @author sun8min
 * @since 2019-04-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sun8min_redpacket_trade_order")
public class RedpacketTradeOrder extends Model<RedpacketTradeOrder> {

    private static final long serialVersionUID = 1L;

    /**
     * 红包交易id
     */
    @TableId(value = "redpacket_trade_order_id", type = IdType.AUTO)
    private BigInteger redpacketTradeOrderId;

    /**
     * 资金转出用户id
     */
    @TableField("from_user_id")
    private BigInteger fromUserId;

    /**
     * 资金转入用户id
     */
    @TableField("to_user_id")
    private BigInteger toUserId;

    /**
     * 交易金额合计（精确到分）
     */
    @TableField("redpacket_trade_amount")
    private BigDecimal redpacketTradeAmount;

    /**
     * 订单交易号
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
        return this.redpacketTradeOrderId;
    }

}
