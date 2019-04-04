package com.sun8min.order.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author sun8min
 * @since 2019-04-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sun8min_order")
public class Order extends Model<Order> {

    private static final long serialVersionUID = 1L;

    /**
     * 订单id
     */
    @TableId(value = "order_id", type = IdType.AUTO)
    private BigInteger orderId;

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
     * 账户交易金额合计（精确到分）
     */
    @TableField("capital_trade_amount")
    private BigDecimal capitalTradeAmount;

    /**
     * 红包交易金额合计（精确到分）
     */
    @TableField("redpacket_trade_amount")
    private BigDecimal redpacketTradeAmount;

    /**
     * 订单支付状态（0:初始化，1:支付中，2:支付成功，3:支付失败，4:取消支付）
     */
    @TableField("order_status")
    private Integer orderStatus;

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
        return this.orderId;
    }

    /**
     * 订单支付状态枚举类
     */
    @Getter
    @AllArgsConstructor
    public enum OrderStatus {
        DRAFT(0, "初始化"),
        PAYING(1, "支付中"),
        PAY_CONFIRM(2, "支付成功"),
        PAY_FAILED(3, "支付失败"),
        PAY_CANCEL(4, "取消支付");

        private int value;
        private String desc;
    }

    private transient List<OrderLine> orderLines = new ArrayList<>();

}
