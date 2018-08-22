package com.jmev.cn.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.jmev.cn.dao.crud.dao.impl.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Entity
@Table(name = "testAdmin")
@Getter
@Setter
public class TestAdmin extends BaseEntity
{
    @Column(name = "USERNAME", length = 20)
    private String username;
    @Column(name = "PASSWORD", length = 20)
    private String password;
}
