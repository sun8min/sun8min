package com.sun8min.capital.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户账户表
 *
 * @author sun8min
 * @date 2019-03-27 01:28:16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Capital implements Serializable {
    /**
     * 账户id
     */
    private Long capitalId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 账户余额合计（精确到万分之一）
     */
    private Long capitalAmount;

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