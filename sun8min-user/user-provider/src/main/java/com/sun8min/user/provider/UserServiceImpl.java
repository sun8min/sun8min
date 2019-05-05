package com.sun8min.user.provider;

import com.sun8min.user.entity.User;
import com.sun8min.user.mapper.UserMapper;
import com.sun8min.user.api.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.dubbo.config.annotation.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author sun8min
 * @since 2019-04-06
 */
@Service(version = "${service.version}")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public User findByNickName(String nickName) {
        return baseMapper.findByNickName(nickName);
    }
}
