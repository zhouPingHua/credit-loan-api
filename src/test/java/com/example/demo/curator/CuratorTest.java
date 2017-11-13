package com.example.demo.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.charset.Charset;

/**
 * Created by zph  Date：2017/11/13.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CuratorTest {

    @Autowired
    private CuratorFramework curatorFramework;

    private static final String path = "/test";
    private static final String value = "test";

    @Test
    public void test(){

        try {
            //1.creat
            System.out.println("create");
            curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path, value
                    .getBytes(Charset.forName("UTF-8")));

            //2.get
            System.out.println("select");
            System.out.println(new String(curatorFramework.getData().forPath(path), Charset.forName("UTF-8")));

            //3.delete
            curatorFramework.delete().forPath(path);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
