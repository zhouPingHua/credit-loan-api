package com.example.demo.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zph  Date：2017/11/10.
 */
public class DemoProcess {

    private static Logger logger = LoggerFactory.getLogger(DemoProcess.class);

    private ThreadPool threadPool;

    private Integer poolsize = 20;

    public DemoProcess(Integer poolsize){
        this.poolsize=poolsize;
        threadPool = new ThreadPool(poolsize);
    }

    /**
     * 停止处理
     * @return void
     */
    public void stop(){
        threadPool.destory();
    }


    /**
     * 测试线程池处理
     * @return void
     */
    public void threadPoolTest(){
        for(int i = 0 ; i<1000 ; i++){
            ZKEvent zkEvent = new ZKEvent("测试线程") {
                @Override
                public void run() throws Exception {
                    logger.info("demo");
                }
            };
            threadPool.submit(zkEvent);
        }
    }


    public static void main(String[] args) {
        DemoProcess demoProcess = new DemoProcess(30);
        demoProcess.threadPoolTest();
//        demoProcess.stop();
    }

}
