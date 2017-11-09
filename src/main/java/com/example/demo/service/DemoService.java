package com.example.demo.service;

import com.example.demo.bean.Demo;

import java.util.List;

/**
 * Created by zph  Date：2017/11/7.
 */
public interface DemoService {

    Demo getStuById(long id);

    Demo selctOne(Long id);

    //分页插件
    List<Demo> findItemByPage(int currentPage,int pageSize);

}
