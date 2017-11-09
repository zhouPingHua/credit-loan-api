package com.example.demo;

import com.example.demo.bean.Demo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by zph  Date：2017/11/7.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void test(){

        //put
        Demo demo = new Demo();
        demo.setId(45L);
        demo.setAddress("jiangxi");
        demo.setAge(44);
        demo.setName("zph");
        redisTemplate.opsForValue().set("zphtest",demo);

        //get
        Demo demo1 = (Demo) redisTemplate.opsForValue().get("zphtest");
        System.out.println(demo1.getName());

        //delete
//        redisTemplate.delete("zphtest");


    }


    @Test
    public void test2(){

//        redisTemplate.delete("zphtest");

        //get
        Demo demo1 = (Demo) redisTemplate.opsForValue().get("zphtest");
        System.out.println(demo1.getName());

    }


    @Test
    public void deleteAll() {
        redisTemplate.delete("demoServiceImpl.selectOne" + 1);
        redisTemplate.delete("demoServiceImpl.selectOne" + 2);
        redisTemplate.delete("demoServiceImpl.selectOne" + 3);
        redisTemplate.delete("demoServiceImpl.selectOne" + 4);
        redisTemplate.delete("demoServiceImpl.selectOne" + 5);
        redisTemplate.delete("demoServiceImpl.selectOne" + 6);

    }



}
