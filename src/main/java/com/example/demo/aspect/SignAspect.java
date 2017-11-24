package com.example.demo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Created by zph  Date：2017/11/22.
 */
@Component
@Aspect
public class SignAspect {

    /**
     * 定义一个切入点
     */
    @Pointcut("@annotation(com.example.demo.annotation.Sign)")
    public void sign(){
    }


    @Before("sign()")
    public void doBeforeSign(JoinPoint point){

    }

    @AfterReturning(pointcut = "sign()",returning = "retValue")
    public void doAfterSign(JoinPoint point,Object retValue){

    }

    private boolean stringIsEmpty(String str){
        return str==null?true:str.trim().length()==0;
    }
}
