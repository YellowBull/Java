package com.jmev.cn.entity.security;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "sys_resource_role")
@Getter
@Setter
@SuppressWarnings("serial")
public class SysResourceRole implements Serializable
{
    @Id
    @Column(name = "id")
    private  int id;
    @Column(name = "role_id", length = 50)
    private String roleId; //角色ID

    @Column(name = "resource_id", length = 50)
    private String resourceId;//资源ID

    @Column(name = "update_time")
    private Date updateTime;//更新时间
}
