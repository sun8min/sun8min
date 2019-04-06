package com.sun8min.user;

import com.sun8min.base.util.EnumUtils;
import com.sun8min.user.entity.User;
import com.sun8min.user.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
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
import java.math.BigInteger;
import java.util.List;
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
    public void crud(){
        userMapper.insert(new User().setUserNickName("AAA"));
        userMapper.insert(new User().setUserNickName("BBB"));
        userMapper.insert(new User().setUserNickName("CCC"));
        userMapper.insert(new User().setUserNickName("DDD"));
        userMapper.insert(new User().setUserNickName("EEE"));

        List<User> users = userMapper.selectList(null);
        User user = users.get(0);
        BigInteger userId = user.getUserId();
        log.info(EnumUtils.getEnumMsg(User.UserSex.class, user.getUserSex()));

        user.setUserNickName("gay");
        userMapper.updateById(user);
        Assert.assertEquals(userMapper.selectById(userId).getUserNickName(), "gay");

        userMapper.deleteById(userId);
    }


    @Test
    public void testVersion(){
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
        System.out.println("appName : " + appName);
        System.out.println("appVersion : " + appVersion);
        System.out.println("serviceVersion : " + serviceVersion);
    }

}