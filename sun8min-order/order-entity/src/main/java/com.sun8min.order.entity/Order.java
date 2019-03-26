package com.sun8min.order.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单表
 *
 * @author sun8min
 * @date 2019-03-27 01:12:51
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order implements Serializable {
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
     * 账户交易金额合计（精确到万分之一）
     */
    private Long capitalTradeAmount;

    /**
     * 红包交易金额合计（精确到万分之一）
     */
    private Long redpacketTradeAmount;

    /**
     * 订单支付状态（0:初始化，1:支付中，2:支付成功，3:取消支付）
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
     * 是否删除（0:正常，1:已删）
     */
    private Integer isDeleted;

    private static final long serialVersionUID = 1L;
}