package com.sun8min.user.api;

import com.sun8min.user.entity.User;

import java.util.List;

public interface UserService {
    int deleteByPrimaryKey(Long userId);

    int insert(User record);

    User selectByPrimaryKey(Long userId);

    List<User> selectAll();

    int updateByPrimaryKey(User record);
}