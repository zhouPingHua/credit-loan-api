package com.example.demo;

import com.example.demo.bean.Demo;
import com.example.demo.service.DemoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by zph  Date：2017/11/9.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PageTest {

    @Autowired
    private DemoService demoService;

    @Test
    public void test(){

        List<Demo> demoList = demoService.findItemByPage(2,2);

        System.out.println(demoList);

    }
}
