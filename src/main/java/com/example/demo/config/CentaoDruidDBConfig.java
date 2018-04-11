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
public class CentaoDruidDBConfig {


    private Logger logger = LoggerFactory.getLogger(CentaoDruidDBConfig.class);

    @Value("${spring.datasource.centao.url}")
    private String dbUrl;

    @Value("${spring.datasource.centao.username}")
    private String username;

    @Value("${spring.datasource.centao.password}")
    private String password;

    @Value("${spring.datasource.centao.driverClassName}")
    private String driverClassName;

    @Value("${spring.datasource.centao.initialSize}")
    private int initialSize;

    @Value("${spring.datasource.centao.minIdle}")
    private int minIdle;

    @Value("${spring.datasource.centao.maxActive}")
    private int maxActive;

    @Value("${spring.datasource.centao.maxWait}")
    private int maxWait;

    @Value("${spring.datasource.centao.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;

    @Value("${spring.datasource.centao.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;

    @Value("${spring.datasource.centao.validationQuery}")
    private String validationQuery;

    @Value("${spring.datasource.centao.testWhileIdle}")
    private boolean testWhileIdle;

    @Value("${spring.datasource.centao.testOnBorrow}")
    private boolean testOnBorrow;

    @Value("${spring.datasource.centao.testOnReturn}")
    private boolean testOnReturn;

    @Value("${spring.datasource.centao.poolPreparedStatements}")
    private boolean poolPreparedStatements;

//    @Value("${spring.datasource.maxPoolPreparedStatementPerConnectionSize}")
//    private int maxPoolPreparedStatementPerConnectionSize;

    @Value("${spring.datasource.centao.filters}")
    private String filters;

    @Value("{spring.datasource.centao.connectionProperties}")
    private String connectionProperties;

    @Bean(name = "centaoDataSource")    //声明其为Bean实例
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
