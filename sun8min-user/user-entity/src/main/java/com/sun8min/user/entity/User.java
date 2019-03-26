package com.sun8min.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 *
 * @author sun8min
 * @date 2019-03-27 00:47:46
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户显示名
     */
    private String userNickName;

    /**
     * 用户真实名
     */
    private String userRealName;

    /**
     * 用户手机号
     */
    private String userPhone;

    /**
     * 用户性别（0:未知，1:男，2:女）
     */
    private Integer userSex;

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