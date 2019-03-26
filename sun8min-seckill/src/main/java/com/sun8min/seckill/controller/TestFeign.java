package com.sun8min.seckill.controller;

//import org.springframework.cloud.openfeign.FeignClient;

import com.sun8min.user.entity.User;

import java.util.List;

//@FeignClient(name = "sun8min-user")
public interface TestFeign {

    List<User> selectAll();
}
