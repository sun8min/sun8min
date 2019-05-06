package com.sun8min.seckill.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 主控制层
 */
@Controller
public class MainController {

    /**
     * ajax请求
     *
     * @return
     */
    @GetMapping("/ajax")
    public String ajax() {
        return "ajax";
    }

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

    @GetMapping("/hello")
    public String hello(ModelMap map) {
        String msg = "不验证登录";
        map.addAttribute("msg", msg);
        map.addAttribute("title", "hello界面");
        return "index";
    }

    @PreAuthorize("hasAuthority('TEST')")//有TEST权限的才能访问
    @GetMapping("/security")
    public String security(ModelMap map) {
        String msg = "TEST权限";
        map.addAttribute("msg", msg);
        map.addAttribute("title", "TEST权限界面");
        return "index";
    }

    @PreAuthorize("hasAuthority('ADMIN')")//必须要有ADMIN权限的才能访问
    @GetMapping("/authorize")
    public String authorize(ModelMap map) {
        String msg = "ADMIN权限";
        map.addAttribute("msg", msg);
        map.addAttribute("title", "ADMIN权限界面");
        return "index";
    }

}
