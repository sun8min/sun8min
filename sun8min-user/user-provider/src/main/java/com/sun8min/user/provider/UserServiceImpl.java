package com.sun8min.user.provider;

import com.sun8min.user.api.UserService;
import com.sun8min.user.dao.UserDao;
import com.sun8min.user.entity.User;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service(version = "${service.version}")
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    public int deleteByPrimaryKey(Long userId) {
        return userDao.deleteByPrimaryKey(userId);
    }

    public int insert(User record) {
        return userDao.insert(record);
    }

    public User selectByPrimaryKey(Long userId) {
        return userDao.selectByPrimaryKey(userId);
    }

    public List<User> selectAll() {
        return userDao.selectAll();
    }

    public int updateByPrimaryKey(User record) {
        return userDao.updateByPrimaryKey(record);
    }
}
