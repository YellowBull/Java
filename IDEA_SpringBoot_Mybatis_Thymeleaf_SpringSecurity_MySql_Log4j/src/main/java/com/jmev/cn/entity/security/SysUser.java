package com.jmev.cn.entity.security;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sys_user")
@Getter
@Setter
@SuppressWarnings("serial")
public class SysUser implements Serializable
{
    @Id
    @Column(name = "id")
    private  int id;
    @Column(name = "name", length = 120)
    private String name; //用户名
    @Column(name = "email", length = 50)
    private String email;//用户邮箱
    @Column(name = "password", length = 120)
    private String password;//用户密码
    @Temporal(TemporalType.DATE)
    @Column(name = "dob", length = 10)
    private Date dob;//时间

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "sysUser")
    private Set<SysRole> sysRoles = new HashSet<SysRole>(0);// 所对应的角色集合
}
