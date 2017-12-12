package com.example.demo.api;

import com.alibaba.fastjson.JSON;
import com.example.demo.annotation.Sign;
import com.example.demo.bean.Demo;
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

    @Autowired
    public DemoController(DemoService demoService,KafkaTemplate kafkaTemplate){
        this.demoService = demoService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @RequestMapping("/demo/{id}")
    @Sign
    public Demo dbDemo(@PathVariable("id") Long id) {
        Demo student = demoService.selctOne(id);
        return student;
    }

    @RequestMapping("/demoAop.go")
    @Sign
    public Demo demoAop(Demo demo) {
        System.out.println("test demoAop方法"+demo.getId());
        return demo;
    }

    @RequestMapping("/demoAop2.go")
    @Sign
    public Demo demoAop2(String name, int age) {
        Demo demo = new Demo();
        demo.setName(name);
        demo.setAge(age);
        System.out.println("test demoAop方法"+demo.getName());
        return demo;
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
