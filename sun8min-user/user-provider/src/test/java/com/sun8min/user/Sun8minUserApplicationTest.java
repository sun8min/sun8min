package com.sun8min.user;

import com.sun8min.user.entity.User;
import com.sun8min.user.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class Sun8minUserApplicationTest {

    @Autowired
    UserMapper userMapper;

    @Test
    @Transactional
    @Rollback
    public void crud() {
        Optional.ofNullable(userMapper.selectById(1)).map(User::getGmtCreate).ifPresent(time -> log.info(time.toString()));
    }

    @Test
    public void testVersion() {
        // 先尝试获取yaml，再获取properties，注意顺序，存在即使/src/main/resource/application.properties文件不存在，获取资源也不为空的情况
        String[] resourceNames = new String[]{"application.yml", "application.yaml", "application.properties"};
        Properties props = new Properties();
        try {
            for (String resourceName : resourceNames) {
                Resource resource = new ClassPathResource(resourceName);
                // 如果文件存在
                if (resource.exists()) {
                    if (resourceName.contains("yml") || resourceName.contains("yaml")) {
                        // yaml文件加载配置
                        YamlPropertiesFactoryBean yamlFactory = new YamlPropertiesFactoryBean();
                        yamlFactory.setResources(resource);
                        props = yamlFactory.getObject();
                    } else {
                        // properties文件加载配置
                        props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceName));
                    }
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String appName = props.getProperty("spring.application.name");
        String appVersion = props.getProperty("spring.application.version");
        String serviceVersion = props.getProperty("service.version");
        log.info("appName: {}" + appName);
        log.info("appVersion: {}" + appVersion);
        log.info("serviceVersion: {}" + serviceVersion);
    }

}