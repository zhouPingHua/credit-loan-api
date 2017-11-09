package com.example.demo.bean;

import java.io.Serializable;

/**
 * Created by zph  Date：2017/11/7.
 */
public class Demo implements Serializable {

    private Long id;
    private String name;
    private Integer age;
    private String address;

    public Demo() {
    }

    public Demo(Long id, String name, Integer age, String address) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString(){
        return id+" "+name+" "+age+" "+address;
    }
}
