package com.example.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
*@EnableTransactionManagement  开启事务管理
*@ComponentScan  扫描注解包
*@MapperScan   开启mybatis接口包扫描
*/

@SpringBootApplication
@EnableTransactionManagement
@ComponentScan(basePackages = "com.example.demo")
@MapperScan(basePackages = "com.example.**.mapper")
public class CreditLoanApiApplication  {
	//需要tomcat启动继承extends SpringBootServletInitializer，去掉自身集成的tomcat插件

	public static void main(String[] args) {
		SpringApplication.run(CreditLoanApiApplication.class, args);
	}
}
