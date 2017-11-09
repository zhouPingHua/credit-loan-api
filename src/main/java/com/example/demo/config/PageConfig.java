package com.example.demo.config;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * Created by zph  Date：2017/11/9.
 */
@Configuration
public class PageConfig {

    @Autowired
    private PageProperties pageProperties;


    //配置mybatis的分页插件pageHelper
    @Bean
    public PageHelper pageHelper(){
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("offsetAsPageNum",pageProperties.getOffsetAsPageNum());
        properties.setProperty("rowBoundsWithCount",pageProperties.getRowBoundsWithCount());
        properties.setProperty("reasonable",pageProperties.getReasonable());
        properties.setProperty("dialect",pageProperties.getDialect());
        pageHelper.setProperties(properties);
        return pageHelper;
    }


}
