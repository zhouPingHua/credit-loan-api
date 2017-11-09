package com.example.demo;

import com.danga.MemCached.MemCachedClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by zph  Date：2017/11/7.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemacheTest {

    @Autowired
    private MemCachedClient memCachedClient;

    @Test
    public void test(){
        memCachedClient.set("zphtest","jiangxi");
        Object zphtest = memCachedClient.get("zphtest");
        System.out.println(zphtest.toString());
        memCachedClient.delete("zphtest");
        zphtest = memCachedClient.get("zphtest");
        System.out.println(zphtest);
    }

}
