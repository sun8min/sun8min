package com.sun8min.product;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * mybatis-plus-generator
 * <p>
 * mysql官方，数据库与java类型对应
 * https://dev.mysql.com/doc/connector-j/5.1/en/connector-j-reference-type-conversions.html
 */
public class MpGenerator {

    // 包名
    private String packageName = "com.sun8min.product";
    // 表名
    private String[] tableNames = {"sun8min_product", "sun8min_shop"};
    // 生成内容（默认全部生成，1：不生成controller)
    private int templateConfigFlag = 1;
    // 目录
    private String outputDir = "src/main/java";

    // 以下在application配置文件修改，这里不需要改动
    private String url;
    private String username;
    private String password;
    private String driverClassName;

    public static void main(String[] args) {
        new MpGenerator().readVersionAndNameFromProperties().generateCode();
    }

    public void generateCode() {
        generateByTables(false, packageName, tableNames);
    }

    private void generateByTables(boolean serviceNameStartWithI, String packageName, String... tableNames) {
        // 全局配置
        GlobalConfig config = new GlobalConfig();
        config.setActiveRecord(true)// ActiveRecord特性，model继承Model<T>
                .setAuthor("sun8min")// 作者
                .setOutputDir(outputDir)// 输出目录
                .setFileOverride(true)// 文件覆盖
                .setEnableCache(false)// XML 二级缓存
                .setBaseResultMap(true)// XML ResultMap
                .setBaseColumnList(true);// XML columList
        if (!serviceNameStartWithI) {
            config.setServiceName("%sService");// 接口前缀去除I
        }
        // 数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL)
                .setUrl(url)
                .setUsername(username)
                .setPassword(password)
                .setDriverName(driverClassName)
                .setTypeConvert(new MySqlTypeConvert() {
                    @Override // 自定义数据库表字段类型转换
                    public IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
                        String t = fieldType.toLowerCase();
                        if (t.contains("bigint") && t.contains("unsigned")) return DbColumnType.BIG_INTEGER;
                        return super.processTypeConvert(globalConfig, fieldType);
                    }
                });
        // 策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig
                .setCapitalMode(true) // 全局大写命名 ORACLE 注意
                .setEntityLombokModel(true) // lombok注解
                .setTablePrefix("sun8min_")// 表前缀
                .setFieldPrefix("is_")// 字段前缀
                .setNaming(NamingStrategy.underline_to_camel)// 表名生成策略
                .setInclude(tableNames);// 需要生成的表名数组
        // 模块配置
        TemplateConfig templateConfig = new TemplateConfig();
        if (templateConfigFlag == 1) {
            templateConfig.setController(null);// 设置 空 OR Null 将不生成该模块
        }
        new AutoGenerator()
                .setGlobalConfig(config)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setTemplate(templateConfig)
                .setPackageInfo(
                        // 包配置
                        new PackageConfig()
                                .setParent(packageName)
                                .setController("controller")
                                .setEntity("entity")
                ).execute();
    }

    /**
     * 读取配置文件的值
     * 不使用${}获取是因为这样就要加@Test注解，maven打包的时候不注意-DskipTests的话就会覆盖文件
     *
     * @return
     */
    private MpGenerator readVersionAndNameFromProperties() {
        String[] resourceNames = new String[]{"application.properties", "application.yml", "application.yaml"};
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Properties props = new Properties();
        try {
            InputStream resourceStream = null;
            for (String resourceName : resourceNames) {
                resourceStream = loader.getResourceAsStream(resourceName);
                if (resourceStream != null) break;
            }
            props.load(resourceStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        url = props.getProperty("spring.datasource.url");
        username = props.getProperty("spring.datasource.username");
        password = props.getProperty("spring.datasource.password");
        driverClassName = props.getProperty("spring.datasource.driver-class-name");
        try {
            outputDir = ResourceUtils.getURL("classpath:").getPath().split("target")[0] + outputDir;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return this;
    }
}