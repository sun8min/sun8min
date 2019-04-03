package com.sun8min.order.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 订单表
 *
 * @author sun8min
 * @date 2019-03-28 13:22:51
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order implements Serializable{
    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 资金转出用户id
     */
    private Long fromUserId;

    /**
     * 资金转入用户id
     */
    private Long toUserId;

    /**
     * 账户交易金额合计（精确到分）
     */
    private BigDecimal capitalTradeAmount;

    /**
     * 红包交易金额合计（精确到分）
     */
    private BigDecimal redpacketTradeAmount;

    /**
     * 订单支付状态（0:初始化，1:支付中，2:支付成功，3:支付失败，4:取消支付）
     */
    private Integer orderStatus;

    /**
     * 订单交易号
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
     * 是否删除（0：否，1：是）
     */
    private Integer isDeleted;

    private static final long serialVersionUID = 1L;

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

        public static String getDescByValue(int value) {
            OrderStatus[] OrderStatusEnums = values();
            for (OrderStatus orderStatusEnum : OrderStatusEnums) {
                if (orderStatusEnum.getValue() == value) {
                    return orderStatusEnum.getDesc();
                }
            }
            return null;
        }
    }

    private List<OrderLine> orderLines = new ArrayList<>();
}