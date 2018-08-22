package com.jmev.cn.entity.security;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.jmev.cn.dao.crud.dao.impl.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "sys_user")
@Getter
@Setter
public class SysUser extends BaseEntity
{
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
