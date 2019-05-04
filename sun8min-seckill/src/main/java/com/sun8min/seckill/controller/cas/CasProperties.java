package com.sun8min.seckill.controller.cas;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * CAS的配置参数
 */
@Getter
@Configuration
public class CasProperties {

    @Value("${server.host.url}")
    private String casServerUrl;

    @Value("${server.host.login_url}")
    private String casServerLoginUrl;

    @Value("${server.host.logout_url}")
    private String casServerLogoutUrl;

    @Value("${app.server.host.url}")
    private String appServerUrl;

    @Value("${app.login.url}")
    private String appLoginUrl;

    @Value("${app.logout.url}")
    private String appLogoutUrl;
}