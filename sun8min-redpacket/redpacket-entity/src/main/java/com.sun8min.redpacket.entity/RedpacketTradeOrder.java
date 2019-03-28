package com.sun8min.redpacket.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户红包交易表
 *
 * @author sun8min
 * @date 2019-03-28 13:26:03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RedpacketTradeOrder implements Serializable {
    /**
     * 红包交易id
     */
    private Long redpacketTradeOrderId;

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
    private BigDecimal redpacketTradeAmount;

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