package com.sun8min.capital.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户账户交易表
 *
 * @author sun8min
 * @date 2019-03-28 13:17:34
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
     * 交易金额合计（精确到分）
     */
    private BigDecimal capitalTradeAmount;

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
}