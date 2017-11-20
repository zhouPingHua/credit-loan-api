package com.example.demo.service.impl;

import com.example.demo.bean.Demo;
import com.example.demo.bean.PageBean;
import com.example.demo.mapper.DemoMapper;
import com.example.demo.service.BaseService;
import com.example.demo.service.DemoService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zph  Date：2017/11/7.
 */
@Service
public class DemoServiceImpl extends BaseService implements DemoService {

    private DemoMapper demoMapper;

    private RedisTemplate<String, Demo> redisTemplate;

    @Autowired
    private DemoServiceImpl(DemoMapper demoMapper, RedisTemplate redisTemplate) {
        this.demoMapper = demoMapper;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Demo selctOne(Long id) {
        logger.info("select one .......testinfo");
        logger.error("select one .......testerror");
        logger.debug("select one .......testdebug");
        Demo result = redisTemplate.opsForValue().get("demoServiceImpl.selectOne" + id);
        if (result == null) {
            Demo demo = new Demo();
            demo.setId(id);
            demo.setAddress("jiangxi");
            demo.setAge(22);
            demo.setName("zph");
            Map<String, Long> params = new HashMap<>();
            params.put("id", id);
            result = demoMapper.selctOne(params);
            if (result == null) {
                Integer code = demoMapper.insert(demo);
                if (code == 1) {
                    logger.info(getClass().getSimpleName() + "方法：selctOne insert执行成功");
                }
            }
            redisTemplate.opsForValue().set("demoServiceImpl.selectOne" + id, demo);
        }
        return result;
    }

    @Override
    public List<Demo> findItemByPage(int currentPage, int pageSize) {
        //设置分页信息，分别是当前页数和每页显示的总记录数【记住：必须在mapper接口中的方法执行之前设置该分页信息】
        PageHelper.startPage(currentPage, pageSize);
        List<Demo> demos =  demoMapper.selectByPage();
        //全部商品
        int countNums = 6;
        PageBean<Demo> pageData = new PageBean<>(currentPage, pageSize, countNums);
        pageData.setItems(demos);
        return pageData.getItems();
    }

    @Override
    public Demo getStuById(long id) {
        Map<String, Long> params = new HashMap<>();
        params.put("id", id);
        Demo result = demoMapper.getStuById(params);
        return result;
    }

}
