package com.sun8min.user;

import org.junit.Test;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Sun8minUserApplicationTest {

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