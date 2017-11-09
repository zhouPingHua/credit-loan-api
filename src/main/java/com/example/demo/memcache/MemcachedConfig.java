package com.example.demo.memcache;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Created by zph  Date：2017/11/7.
 */
@Component
public class MemcachedConfig {

    @Autowired
    public SockIOPoolConfig sockIOPoolConfig;

    @Bean
    public SockIOPool sockIOPool(){
        //获取连接池的实例
        SockIOPool pool = SockIOPool.getInstance();
        //服务器列表及其权重
        String[] servers = sockIOPoolConfig.getServers();
        Integer[] weights = sockIOPoolConfig.getWeights();
        //设置服务器信息
        pool.setServers(servers);
        pool.setWeights(weights);
        //设置初始连接数、最小连接数、最大连接数、最大处理时间
        pool.setInitConn(sockIOPoolConfig.getInitConn());
        pool.setMinConn(sockIOPoolConfig.getMinConn());
        pool.setMaxConn(sockIOPoolConfig.getMaxConn());
        //设置连接池守护线程的睡眠时间
        pool.setMaintSleep(sockIOPoolConfig.getMaintSleep());
        //设置TCP参数，连接超时
        pool.setNagle(sockIOPoolConfig.isNagle());
        pool.setSocketConnectTO(sockIOPoolConfig.getSocketTO());
        //初始化并启动连接池
        pool.initialize();
        return pool;
    }


    @Bean
    @ConditionalOnBean(SockIOPool.class)
    public MemCachedClient memCachedClient(){
        return new MemCachedClient();
    }


}
