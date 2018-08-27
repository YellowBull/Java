package com.jmev.cn.entity.security;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "sys_role")
@Getter
@Setter
@SuppressWarnings("serial")
public class SysRole implements Serializable
{
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "name", length = 100)
    private String name;//角色名称

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uid", nullable = false)
    private SysUser sysUser;//角色对应的用户实体

}
