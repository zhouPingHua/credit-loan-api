package com.example.demo.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.bean.Demo;
import com.example.demo.mapper.DemoMapper;
import com.example.demo.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * Created by zph  Date：2017/11/7.
 */
@RestController
public class DemoController extends BaseController{


    private DemoService demoService;
    private KafkaTemplate kafkaTemplate;

    public DemoController(DemoService demoService,KafkaTemplate kafkaTemplate){
        this.demoService = demoService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @RequestMapping("/demo/{id}")
    public Demo dbDemo(@PathVariable("id") Long id) {
        Demo student = demoService.selctOne(id);
        return student;
    }

    @RequestMapping("/selectOne")
    public String selectOne2() {
        Demo student = demoService.getStuById(1L);
        String strDemo = JSON.toJSONString(student);
        return strDemo;
    }

    @RequestMapping(value = "/send", method = RequestMethod.GET)
    public void send(@RequestParam(required = true) String topic, @RequestParam(required = true) String message) {
        kafkaTemplate.send(topic, message);
    }


}
