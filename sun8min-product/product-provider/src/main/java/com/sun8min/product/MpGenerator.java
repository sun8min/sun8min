package com.sun8min.product;

import com.baomidou.mybatisplus.generator.config.*;
import org.junit.Test;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * mybatis-plus-generator
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MpGenerator {

    // 包名
    private String packageName = "com.sun8min.product";
    // 表名
    private String[] tableNames = {"sun8min_product", "sun8min_shop"};
    // 目录
    private String outputDir = "src/main/java";

    // 以下在application配置文件修改，这里不需要改动
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Test
    public void generateCode() {
        generateByTables(false, packageName, tableNames);
    }

    private void generateByTables(boolean serviceNameStartWithI, String packageName, String... tableNames) {
        GlobalConfig config = new GlobalConfig();
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL)
                .setUrl(url)
                .setUsername(username)
                .setPassword(password)
                .setDriverName(driverClassName);
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig
                .setCapitalMode(true) // 全局大写命名 ORACLE 注意
                .setEntityLombokModel(true)
                .setTablePrefix("sun8min_")// 表前缀
                .setNaming(NamingStrategy.underline_to_camel)
                .setInclude(tableNames);//修改替换成你需要的表名，多个表名传数组
        config.setActiveRecord(false)
                .setAuthor("sun8min")
                .setOutputDir(outputDir)
                .setFileOverride(true)
                .setEnableCache(false)// XML 二级缓存
                .setBaseResultMap(true)// XML ResultMap
                .setBaseColumnList(true);// XML columList
        if (!serviceNameStartWithI) {
            config.setServiceName("%sService");
        }
        new AutoGenerator().setGlobalConfig(config)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(
                        new PackageConfig()
                                .setParent(packageName)
                                .setController("controller")
                                .setEntity("entity")
                ).execute();
    }
}