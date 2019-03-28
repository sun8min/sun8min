package com.sun8min.seckill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
// 启用服务注册与发现
//@EnableEurekaClient
// 启用feign进行远程调用
//@EnableFeignClients
public class Sun8minSeckillApplication {

	public static void main(String[] args) {
		SpringApplication.run(Sun8minSeckillApplication.class, args);
	}

}
