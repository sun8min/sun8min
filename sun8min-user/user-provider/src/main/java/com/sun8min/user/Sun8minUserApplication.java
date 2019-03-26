package com.sun8min.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// 存放mapper的目录
@MapperScan("com.sun8min.user.dao")
// 使用eureka推荐该注解，否则@EnableDiscoveryClient
//@EnableEurekaClient
public class Sun8minUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(Sun8minUserApplication.class, args);
    }

}
