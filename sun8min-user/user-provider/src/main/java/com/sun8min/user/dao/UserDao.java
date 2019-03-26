package com.sun8min.user.dao;

import com.sun8min.user.entity.User;

import java.util.List;

public interface UserDao {
    int deleteByPrimaryKey(Long userId);

    int insert(User record);

    User selectByPrimaryKey(Long userId);

    List<User> selectAll();

    int updateByPrimaryKey(User record);
}