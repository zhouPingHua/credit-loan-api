package com.example.demo.config;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by zph  Date：2017/11/13.
 */
@Configuration
public class CuratorConfig {

    @Value("${zookeeper.connect}")
    private String ZK_ADDRESS;



//    @Bean
//    public CuratorFramework curatorFramework(){
//        CuratorFramework client = CuratorFrameworkFactory.newClient(
//                ZK_ADDRESS,
//                new RetryNTimes(10, 5000)
//        );
//        client.start();
//        System.out.println("zk client start successfully!");
//        return client;
//    }
}
