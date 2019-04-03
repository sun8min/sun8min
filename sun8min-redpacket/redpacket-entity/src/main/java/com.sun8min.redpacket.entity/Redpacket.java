package com.sun8min.redpacket.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户红包表
 *
 * @author sun8min
 * @date 2019-03-28 13:26:03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Redpacket implements Serializable {
    /**
     * 红包id
     */
    private Long redpacketId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 红包余额合计（精确到分）
     */
    private BigDecimal redpacketAmount;

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