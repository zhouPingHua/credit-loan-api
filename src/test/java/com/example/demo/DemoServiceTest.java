package com.example.demo;

import com.example.demo.bean.Demo;
import com.example.demo.service.DemoService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by zph  Date：2017/11/8.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoServiceTest {


    @Autowired
    private DemoService demoService;

    @Test
    public void test(){
        Demo demo = demoService.getStuById(1L);
        System.out.println(demo);
    }
}
