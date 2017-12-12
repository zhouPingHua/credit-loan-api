package com.example.demo.gc;

import org.junit.Test;

/**
 * @author zph  on 2017/12/11
 */
public class PretenureSizeTest {

    private static final int _1M = 1024*1024;

    @Test
    /**
    * -Xmx 3800m
    */

    public void testPretenure() {
        byte[] allacation;
        allacation = new byte[1*_1M];
    }
}
