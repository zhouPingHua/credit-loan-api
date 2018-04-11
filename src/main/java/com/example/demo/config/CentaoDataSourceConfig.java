package com.example.demo.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @author zph  on 2018/4/11
 */
@Configuration
@MapperScan(basePackages = "com.example.demo.mapper.centao", sqlSessionTemplateRef = "centaoSqlSessionTemplate")
public class CentaoDataSourceConfig {

//    @Bean(name = "centaoDataSource")
//    @ConfigurationProperties(prefix = "spring.datasource.centao")
//    public DataSource setDataSource() {
//        return DataSourceBuilder.create().build();
//    }

    @Bean(name = "centaoTransactionManager")
    public DataSourceTransactionManager setTransactionManager(@Qualifier("centaoDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "centaoSqlSessionFactory")
    public SqlSessionFactory setSqlSessionFactory(@Qualifier("centaoDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:com/example/mapper/centao/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "centaoSqlSessionTemplate")
    public SqlSessionTemplate setSqlSessionTemplate(@Qualifier("centaoSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
