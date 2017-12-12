package com.example.demo.interceptor;

import com.example.demo.interceptor.converter.ParameterRequestWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;

/**
 * Created by zph  Date：2017/11/22.
 */
//@Component
//@WebFilter(filterName="myFilter",urlPatterns="/demoAop.go")
public class MyFilter implements Filter {

    @Override
    public void destroy() {
        System.out.println("过滤器销毁");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        System.out.println("执行过滤操作");
        ParameterRequestWrapper parameterRequestWrapper = null;
        if(servletRequest instanceof ParameterRequestWrapper){
            parameterRequestWrapper = (ParameterRequestWrapper) servletRequest;
            parameterRequestWrapper.setParameter("name","zph");
        }
        chain.doFilter(servletRequest, response);
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
        System.out.println("过滤器初始化");
    }

}
