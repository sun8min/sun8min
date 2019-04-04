package com.sun8min.redpacket;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * mybatis-plus-generator
 * <p>
 * mysql官方，数据库与java类型对应
 * https://dev.mysql.com/doc/connector-j/5.1/en/connector-j-reference-type-conversions.html
 */
public class MpGenerator {

    // 包名
    private String packageName = "com.sun8min.redpacket";
    // 数据库查表语句
    // set @table_schema = "sun8min_redpacket";
    // select group_concat(concat('"',table_name,'"')) from information_schema.tables where table_schema=@table_schema and table_name != "undo_log";
    // 表名
    private String[] tableNames = {"sun8min_redpacket","sun8min_redpacket_trade_order"};
    // 生成内容（默认全部生成，1：不生成controller)
    private int templateConfigFlag = 1;
    // 目录
    private String outputDir = "/src/main/java";

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
        config.setActiveRecord(true)// ActiveRecord特性，让model继承Model<T>
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
                        if (t.contains("int") && t.contains("unsigned")) return DbColumnType.LONG;
                        return super.processTypeConvert(globalConfig, fieldType);
                    }
                });
        // 策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig
                .setCapitalMode(true)// 全局大写命名 ORACLE 注意
                .setEntityLombokModel(true)// lombok注解
                .setTablePrefix("sun8min_")// 表前缀
                .setEntityBooleanColumnRemoveIsPrefix(true)// boolean字段移除is前缀，需要配合TableField
                .setEntityTableFieldAnnotationEnable(true) // 导入TableField注解包
                .setVersionFieldName("version")// 乐观锁属性名称
                .setLogicDeleteFieldName("is_deleted")// 逻辑删除属性名称
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
                                .setService("api")
                                .setServiceImpl("provider")
                ).execute();
    }

    /**
     * 读取配置文件的值
     * 不使用${}获取是因为这样就要加@Test注解，maven打包的时候不注意-DskipTests的话就会覆盖文件
     *
     * @return
     */
    private MpGenerator readVersionAndNameFromProperties() {
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