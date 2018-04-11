package com.example.demo.mapper.base;

import com.example.demo.bean.Demo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by zph  Date：2017/11/7.
 */
public interface DemoMapper extends Mapper<Demo> {

    Demo getStuById(Map<String, Long> param);

    Demo selctOne(Map<String, Long> param);

    List<Demo> selectByPage();

    int insertDemo(Demo demo);
}
