package com.sun8min.capital.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户账户交易表
 *
 * @author sun8min
 * @date 2019-03-27 01:28:16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CapitalTradeOrder implements Serializable {
    /**
     * 账户交易id
     */
    private Long capitalTradeOrderId;

    /**
     * 资金转出用户id
     */
    private Long fromUserId;

    /**
     * 资金转入用户id
     */
    private Long toUserId;

    /**
     * 交易金额合计（精确到万分之一）
     */
    private Long capitalTradeAmount;

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