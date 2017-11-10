package com.example.demo.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by zph  Date：2017/11/10.
 * <p>
 * 事件处理线程池
 */
public class ThreadPool {

    private static Logger logger = LoggerFactory.getLogger(ThreadPool.class);

    private ThreadPoolExecutor pool;
    private static AtomicInteger index = new AtomicInteger(0);

    /**
     * 初始化线程池，这里采用corePoolSize=maximumPoolSize，
     * 并且使用LinkedBlockingQueue无限大小的阻塞队列来处理事件
     * ThreadPoolExecutor.
     *
     * @param poolSize
     */
    public ThreadPool(Integer poolSize) {
        pool = new java.util.concurrent.ThreadPoolExecutor(
                poolSize,         //corePoolSize 核心线程池大小
                poolSize,         //aximumPoolSize 最大线程池大小
                30,               //keepAliveTime 线程池中超过corePoolSize数目的空闲线程最大存活时间
                TimeUnit.MINUTES, //TimeUnit keepAliveTime时间单位 这里是秒
                new LinkedBlockingQueue<Runnable>(),   //workQueue 阻塞队列
                new DemoThreadFactory());           //线程工厂
    }


    /**
     * 销毁线程池
     *
     * @return void
     */
    public void destory() {
        if (pool != null) {
            pool.shutdown();
        }
    }

    /**
     * 处理事件
     *
     * @param zkEvent
     * @return void
     */
    public void submit(final ZKEvent zkEvent) {
        pool.submit(new Runnable() {
            @Override
            public void run() {
                int eventId = index.incrementAndGet();
                try {
                    logger.debug("Handling event-" + eventId + " " + zkEvent);
                    zkEvent.run();
                } catch (Exception e) {
                    logger.error("Error handling event [" + zkEvent + "]", e);
                }
                logger.debug("Handled the event-" + eventId);
            }
        });
    }


    /**
     * 私有内部类，线程创建工厂
     */
    private class DemoThreadFactory implements ThreadFactory {
        private AtomicInteger count = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            String threadName = "Client-Thread-" + count.addAndGet(1);
            t.setName(threadName);
            return t;
        }
    }

}
