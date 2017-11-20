package com.example.demo;

import com.example.demo.bean.Demo;
import com.example.demo.mapper.DemoMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zph  Date：2017/11/20.
 *
 * 数据库mapper测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoMapperTest {

    @Autowired
    private DemoMapper mapper;

    @Test
    public void test() {
        Assert.assertNotNull(mapper);
        List<Demo> demos = mapper.selectAll();
        Assert.assertNotNull(demos.size() > 0);
        Map<String, Long> params = new HashMap<>();
        params.put("id", 1L);
        Demo demo = mapper.getStuById(params);
        System.out.println(demo.getId());
    }

    @Test
    public void test2(){

        List<Demo>  demos = mapper.selectAll();
        System.out.println(demos);

    }

}
