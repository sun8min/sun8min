package com.sun8min.seckill.config;

import com.alibaba.fescar.spring.annotation.GlobalTransactionScanner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FescarConfig {

    @Bean
    public GlobalTransactionScanner globalTransactionScanner(){
        return new GlobalTransactionScanner("seckill-gts-fescar-example", "my_test_tx_group");
    }
}
