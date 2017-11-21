package com.example.demo;

import com.example.demo.bean.Demo;
import com.example.demo.mapper.DemoMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zph  Date：2017/11/21.
 *
 * MybatisHelper 测试
 */
public class MybatisHelperTest {


    @Test
    public void test(){

        SqlSession session = MybatisHelper.getSqlSession();
        DemoMapper demoMapper = session.getMapper(DemoMapper.class);

        Map<String,Long> params = new HashMap<>();
        params.put("id",1l);
        Demo demo = demoMapper.getStuById(params);
        System.out.println(demo.toString());
        List<Demo> demoList = demoMapper.selectAll();
        System.out.println(demoList.size());
    }
}
