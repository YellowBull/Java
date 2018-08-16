package com.jmev.cn.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.jmev.cn.dao.crud.dao.impl.BaseEntity;

@SuppressWarnings("serial")
@Entity
@Table(name = "testAdmin")
public class TestAdmin extends BaseEntity implements Serializable
{
    @Column(name = "username", length = 20)
    private String username;
    @Column(name = "passworld", length = 20)
    private String passworld;
    
    public TestAdmin()
    {
        super();
    }
    public TestAdmin(String username, String passworld)
    {
        super();
        this.username = username;
        this.passworld = passworld;
    }
    public String getUsername()
    {
        return username;
    }
    public void setUsername(String username)
    {
        this.username = username;
    }
    public String getPassworld()
    {
        return passworld;
    }
    public void setPassworld(String passworld)
    {
        this.passworld = passworld;
    }
}
