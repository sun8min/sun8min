package com.sun8min.redpacket.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户红包表
 *
 * @author sun8min
 * @date 2019-03-27 00:55:43
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
     * 红包余额合计（精确到万分之一）
     */
    private Long redpacketAmount;

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