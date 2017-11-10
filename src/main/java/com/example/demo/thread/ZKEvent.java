package com.example.demo.thread;

/**
 * Created by zph  Date：2017/11/10.
 */
public abstract class ZKEvent {

    private String description;

    public ZKEvent(String description) {
        this.description = description;
    }

    public abstract void run() throws Exception;

    @Override
    public String toString() {
        return "ZKEvent[" + description + "]";
    }

}
