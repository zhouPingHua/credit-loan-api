package com.example.demo;

import com.example.demo.bean.Demo;
import com.example.demo.mapper.DemoMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zph  Date：2017/11/8.
 */
public class MybatisTest {

    private static SqlSessionFactory sqlSessionFactory;

    @Test
    public void test(){

        Reader reader = null;
        try {
            reader = Resources.getResourceAsReader("OracleConfiguration_test.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);

            SqlSession sqlSession = sqlSessionFactory.openSession();
            DemoMapper demoMapper = sqlSession.getMapper(DemoMapper.class);
            Map<String, Long> param = new HashMap<>();
            param.put("id",10l);
            Demo demo = demoMapper.getStuById(param);
            System.out.println(demo.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
