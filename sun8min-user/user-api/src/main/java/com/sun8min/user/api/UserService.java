package com.sun8min.user.api;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sun8min.user.entity.User;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author sun8min
 * @since 2019-04-06
 */
public interface UserService extends IService<User> {

    /**
     * 根据用户名查找用户
     *
     * @param nickName 用户名
     * @return 用户
     */
    User findByNickName(String nickName);
}
