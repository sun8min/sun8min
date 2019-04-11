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
 * @since 2019-04-06
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
     * 商店id
     */
    @TableField("shop_id")
    private BigInteger shopId;

    /**
     * 交易金额合计（精确到分）
     */
    @TableField("order_trade_amount")
    private BigDecimal orderTradeAmount;

    /**
     * 订单支付渠道（1：账户，2：支付宝，3：微信）
     */
    @TableField("order_pay_channel")
    private Integer orderPayChannel;

    /**
     * 渠道支付单号
     */
    @TableField("order_pay_no")
    private String orderPayNo;

    /**
     * 支付时间
     */
    @TableField("order_pay_time")
    private LocalDateTime orderPayTime;

    /**
     * 订单状态（0:初始化，1：等待支付，2:支付中，3:支付成功，4:支付失败，5:取消支付，6：支付超时被系统关闭）
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
     * 订单支付渠道枚举类（1：账户，2：支付宝，3：微信）
     */
    @Getter
    @AllArgsConstructor
    public enum OrderPayChannel {
        ACCOUNT(1, "账户"),
        AILPAY(2, "支付宝"),
        WECHAT(3, "微信");

        private int code;
        private String msg;
    }

    /**
     * 订单状态枚举类（0:初始化，1：等待支付，2:支付中，3:支付成功，4:支付失败，5:取消支付，6：支付超时被系统关闭）
     */
    @Getter
    @AllArgsConstructor
    public enum OrderStatus {
        DRAFT(0, "初始化"),
        WAIT_PAY(1, "等待支付"),
        PAYING(2, "支付中"),
        PAY_CONFIRMED(3, "支付成功"),
        PAY_FAILED(4, "支付失败"),
        PAY_CANCELED(5, "取消支付"),
        PAY_TIMEOUT_CLOSED_BY_SYSTEM(6, "支付超时被系统关闭");

        private int code;
        private String msg;
    }

    /**
     * 订单项（非数据库字段，使用注解@TableField，如果使用transient关键字，不会被序列化，即null）
     */
    @TableField(exist = false)
    private List<OrderLine> orderLines = new ArrayList<>();

}
