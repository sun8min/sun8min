package com.sun8min.user.mapper;

import com.sun8min.user.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author sun8min
 * @since 2019-04-06
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据用户名查找用户
     * @param nickName 用户名
     * @return 用户
     */
    User findByNickName(String nickName);
}
