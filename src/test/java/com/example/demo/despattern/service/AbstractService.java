package com.example.demo.despattern.service;

import com.example.demo.despattern.Logdp;

/**
 * @author zph  on 2018/3/12
 */
public abstract class AbstractService extends Logdp {

    public abstract void write();

    public abstract void read();
}
