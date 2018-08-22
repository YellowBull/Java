package com.jmev.cn.entity.security;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.jmev.cn.dao.crud.dao.impl.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "sys_resource_role")
@Getter
@Setter
public class SysResourceRole extends BaseEntity
{
    @Column(name = "role_id", length = 50)
    private String roleId; //角色ID

    @Column(name = "resource_id", length = 50)
    private String resourceId;//资源ID

    @Column(name = "update_time")
    private Date updateTime;//更新时间
}
