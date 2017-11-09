package com.example.demo.mapper;

import com.example.demo.bean.Demo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by zph  Date：2017/11/7.
 */
public interface DemoMapper {

    Demo getStuById(Map<String, Long> param);

    Demo selctOne(Map<String, Long> param);

    List<Demo> selectByPage();

    Integer insert(Demo demo);
}
