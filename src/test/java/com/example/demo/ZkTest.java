package com.example.demo;

import com.alibaba.fastjson.JSONObject;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestMapping;

import java.nio.charset.Charset;

/**
 * Created by zph  Date：2017/11/10.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class ZkTest {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CuratorFramework curatorFramework;

    String path = "/test";
    String value = "test";

    @Test
    public void create() {
        try {
            curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path, value
                    .getBytes(Charset.forName("UTF-8")));
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
    }


    @Test
    public void select() {
        try {
            String str = new String(curatorFramework.getData().forPath(path), Charset.forName("UTF-8"));
            System.out.println(str);
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
    }


    @Test
    public void delete() {
        try {
            curatorFramework.delete().forPath(path);
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
    }
}
