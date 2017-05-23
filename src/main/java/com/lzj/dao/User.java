package com.lzj.dao;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/23 0023.
 */
public class User implements Serializable{
    private Integer id;
    private String name;
    private Integer password;
    private String emil;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Integer getPassword() {
        return password;
    }

    public void setPassword(Integer password) {
        this.password = password;
    }

    public String getEmil() {
        return emil;
    }

    public void setEmil(String emil) {
        this.emil = emil;
    }

    public User(Integer id, String name, Integer password, String emil) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.emil = emil;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password=" + password +
                ", emil='" + emil + '\'' +
                '}';
    }
}
