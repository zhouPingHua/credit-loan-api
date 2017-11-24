package com.example.demo.config;

import com.example.demo.interceptor.MyFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

/**
 * Created by zph  Date：2017/11/22.
 */
@Configuration
public class FileConfig {



    /**
     * 配置过滤器
     * @return
     */
    @Bean
    public FilterRegistrationBean someFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(sessionFilter());
        registration.addUrlPatterns("/selectOne","/demoAop.go");
        registration.addInitParameter("paramName", "paramValue");
        registration.setName("myFilter");
        return registration;
    }


    /**
     * 创建一个bean
     * @return
     */
    @Bean(name = "myFilter")
    public Filter sessionFilter() {
        return new MyFilter();
    }

}
