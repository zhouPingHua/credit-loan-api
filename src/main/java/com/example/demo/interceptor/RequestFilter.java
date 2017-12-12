package com.example.demo.interceptor;

import com.example.demo.interceptor.converter.ParameterRequestWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zph  on 2017/12/12
 */
public class RequestFilter implements Filter {
    private final static Logger LOGGER = LoggerFactory.getLogger(RequestFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        ParameterRequestWrapper requestWrapper = new ParameterRequestWrapper(request);
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        filterChain.doFilter(requestWrapper, response);
    }

    @Override
    public void destroy() {

    }
}
