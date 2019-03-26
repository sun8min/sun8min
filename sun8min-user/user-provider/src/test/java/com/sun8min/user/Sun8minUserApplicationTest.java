package com.sun8min.user;

import com.sun8min.user.api.UserService;
import com.sun8min.user.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@SpringBootTest
@RunWith(SpringRunner.class)
public class Sun8minUserApplicationTest {

    @Autowired
    UserService userService;

    @Test
    @Transactional
    public void crud(){
        userService.insert(new User("AAA"));
        userService.insert(new User("BBB"));
        userService.insert(new User("CCC"));
        userService.insert(new User("DDD"));
        userService.insert(new User("EEE"));

        User user = userService.selectByPrimaryKey(6L);
        user.setUserNickName("gay");
        userService.updateByPrimaryKey(user);
        Assert.assertEquals(userService.selectByPrimaryKey(6L).getUserNickName(), "gay");

        userService.deleteByPrimaryKey(6L);
    }


    @Test
    public void testVersion(){
        String resourceName = "application.properties";
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Properties props = new Properties();
        try {
            InputStream resourceStream = loader.getResourceAsStream(resourceName);
            props.load(resourceStream);
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