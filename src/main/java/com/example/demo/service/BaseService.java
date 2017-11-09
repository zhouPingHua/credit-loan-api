package com.example.demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zph  Date：2017/11/7.
 */
@Transactional(rollbackFor = Exception.class)
public abstract class BaseService {
    public Logger logger = LoggerFactory.getLogger(getClass());
}
