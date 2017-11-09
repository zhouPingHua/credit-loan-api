package com.example.demo;

import com.example.demo.bean.Demo;
import org.junit.Test;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by zph  Date：2017/11/7.
 */
public class RedisTemplateTest {


    //注意afterPropertySet方法的调用!!初始化
    @Test
    public void test() {
        JedisConnectionFactory factory = new JedisConnectionFactory();
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(5);
        config.setMaxIdle(4);
        config.setMinIdle(1);
        config.setMaxWaitMillis(3000);
        factory.setPoolConfig(config);
        factory.setUsePool(true);
        factory.setHostName("192.168.1.51");
        factory.setPort(10100);
        //important !!!!
        factory.afterPropertiesSet();
        RedisTemplate<String, Demo> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        RedisSerializer<String> stringRedisSerializer = new StringRedisSerializer();
        RedisSerializer<Object> objectRedisSerializer = new GenericJackson2JsonRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(objectRedisSerializer);
        //important !!!!
        redisTemplate.afterPropertiesSet();
        Demo newDemo = new Demo(1L, "zph", 23, "jiangxi");
        redisTemplate.opsForValue().set("demoServiceImpl.selectOne" + 1, newDemo);
        Demo demo = redisTemplate.opsForValue().get("demoServiceImpl.selectOne" + 1);
        System.out.println(demo.getId());
    }
}
