package com.example.demo.mapper.centao;

import com.example.demo.bean.Demo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author zph  on 2018/4/11
 */
public interface CentaoMapper extends Mapper<Demo> {

    Demo getStuById(Map<String, Long> param);

    Demo selctOne(Map<String, Long> param);

    List<Demo> selectByPage();

    int insertDemo(Demo demo);
}
