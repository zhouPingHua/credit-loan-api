package com.example.demo.despattern.work;


import com.example.demo.despattern.db.SqlSession;

/**
 * @author zph  on 2018/3/12
 */
public abstract class BaseServiceDb {

    public SqlSession sqlSession;

    public BaseServiceDb(){
        sqlSession = new SqlSession();
    }
}
