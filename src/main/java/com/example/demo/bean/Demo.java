package com.example.demo.bean;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by zph  Date：2017/11/7.
 */
@Data
@Table(name = "tb_demo")
public class Demo implements Serializable {

    @Id
    private Long id;
    @Column(name="NAME")
    private String name;
    @Column(name="AGE")
    private Integer age;
    @Column(name="ADDRESS")
    private String address;

    public Demo() {
    }

    public Demo(Long id, String name, Integer age, String address) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.address = address;
    }

    @Override
    public String toString(){
        return id+" "+name+" "+age+" "+address;
    }
}
