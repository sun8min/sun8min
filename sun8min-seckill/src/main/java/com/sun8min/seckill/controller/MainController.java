package com.sun8min.seckill.controller;

import com.sun8min.user.api.UserService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 主控制层
 */
@Controller
public class MainController {

    @Reference(version = "${service.version}")
    UserService userService;

    /**
     * 主界面
     *
     * @param map
     * @return
     */
    @GetMapping("/")
    public String index(ModelMap map) {
        String msg = "hello，this is first messge";
        map.addAttribute("msg", msg);
        map.addAttribute("title", "主界面");
        return "index";
    }

    /**
     * 登陆
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        // TODO 用户登陆，用于购买商品等操作
//        userService.
        return null;
    }

}
