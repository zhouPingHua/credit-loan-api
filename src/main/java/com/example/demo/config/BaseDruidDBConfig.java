package com.example.demo.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Created by zph  Date：2017/11/22.
 */
@Configuration
public class BaseDruidDBConfig {


    private Logger logger = LoggerFactory.getLogger(BaseDruidDBConfig.class);

    @Value("${spring.datasource.base.url}")
    private String dbUrl;

    @Value("${spring.datasource.base.username}")
    private String username;

    @Value("${spring.datasource.base.password}")
    private String password;

    @Value("${spring.datasource.base.driverClassName}")
    private String driverClassName;

    @Value("${spring.datasource.base.initialSize}")
    private int initialSize;

    @Value("${spring.datasource.base.minIdle}")
    private int minIdle;

    @Value("${spring.datasource.base.maxActive}")
    private int maxActive;

    @Value("${spring.datasource.base.maxWait}")
    private int maxWait;

    @Value("${spring.datasource.base.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;

    @Value("${spring.datasource.base.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;

    @Value("${spring.datasource.base.validationQuery}")
    private String validationQuery;

    @Value("${spring.datasource.base.testWhileIdle}")
    private boolean testWhileIdle;

    @Value("${spring.datasource.base.testOnBorrow}")
    private boolean testOnBorrow;

    @Value("${spring.datasource.base.testOnReturn}")
    private boolean testOnReturn;

    @Value("${spring.datasource.base.poolPreparedStatements}")
    private boolean poolPreparedStatements;

//    @Value("${spring.datasource.maxPoolPreparedStatementPerConnectionSize}")
//    private int maxPoolPreparedStatementPerConnectionSize;

    @Value("${spring.datasource.base.filters}")
    private String filters;

    @Value("{spring.datasource.base.connectionProperties}")
    private String connectionProperties;

    @Bean(name = "baseDataSource")    //声明其为Bean实例
    @Primary  //在同样的DataSource中，首先使用被标注的DataSource
    public DataSource dataSource(){
        DruidDataSource datasource = new DruidDataSource();

        datasource.setUrl(this.dbUrl);
        datasource.setUsername(username);
        datasource.setPassword(password);
        datasource.setDriverClassName(driverClassName);

        //configuration
        datasource.setInitialSize(initialSize);
        datasource.setMinIdle(minIdle);
        datasource.setMaxActive(maxActive);
        datasource.setMaxWait(maxWait);
        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        datasource.setValidationQuery(validationQuery);
        datasource.setTestWhileIdle(testWhileIdle);
        datasource.setTestOnBorrow(testOnBorrow);
        datasource.setTestOnReturn(testOnReturn);
        datasource.setPoolPreparedStatements(poolPreparedStatements);
//        datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        try {
            datasource.setFilters(filters);
        } catch (SQLException e) {
            logger.error("druid configuration initialization filter", e);
        }
        datasource.setConnectionProperties(connectionProperties);

        return datasource;
    }
}
