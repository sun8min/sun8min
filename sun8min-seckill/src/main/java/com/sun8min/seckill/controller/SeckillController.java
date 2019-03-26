package com.sun8min.seckill.controller;

import com.sun8min.user.api.UserService;
import com.sun8min.user.entity.User;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// consumer://172.20.10.5/com.sun8min.user.api.gege.userService2?application=consumer&category=consumers&check=false&dubbo=2.5.3&interface=com.sun8min.user.api.gege.userService2&methods=selectAll&pid=2385&revision=1.0.0&side=consumer&timestamp=1553422997200&version=1.0.0
@RestController
public class SeckillController {

//    @Autowired
//    TestFeign testFeign;

    @Reference(version = "${user.service.version}")
    UserService userService;

    @GetMapping("/index")
    public String index(String name){
//        List<User> users = testFeign.selectAll();
//        users.forEach(System.out::println);

        List<User> users = userService.selectAll();
        users.forEach(System.out::println);
        System.out.println();

        return "hello "+name+"，this is first messge";
    }
}
