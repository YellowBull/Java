package com.jmev.cn.entity.security;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.jmev.cn.dao.crud.dao.impl.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "sys_resource")
@Getter
@Setter
public class SysResource extends BaseEntity
{
    @Column(name = "resource_url", length = 1000)
    private String resourceUrl;//url

    @Column(name = "resource_id", length = 50)
    private String resourceId;//资源ID

    @Column(name = "remark", length = 200)
    private String remark;//备注

    @Column(name = "resource_name", length = 400)
    private String resourceName;//资源名称

    @Column(name = "method_name", length = 400)
    private String methodName;//资源所对应的方法名

    @Column(name = "method_path", length = 1000)
    private String methodPath;//资源所对应的包路径
}
