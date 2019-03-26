package com.sun8min.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

// 添加了数据库组件，所以autoconfig会去读取数据源配置，而项目没有配置数据源，所以会导致异常出现
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableEurekaServer
public class Sun8minEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(Sun8minEurekaApplication.class, args);
    }

}
