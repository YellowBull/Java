package com.jmev.cn.entity.security;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.jmev.cn.dao.crud.dao.impl.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "sys_role")
@Getter
@Setter
public class SysRole extends BaseEntity
{
    @Column(name = "name", length = 100)
    private String name;//角色名称

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uid", nullable = false)
    private SysUser sysUser;//角色对应的用户实体

}
