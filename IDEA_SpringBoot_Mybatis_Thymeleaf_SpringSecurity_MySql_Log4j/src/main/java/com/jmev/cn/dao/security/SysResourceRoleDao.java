package com.jmev.cn.dao.security;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jmev.cn.entity.security.SysResourceRole;

@Repository("sysResourceRoleDao")
public interface SysResourceRoleDao extends JpaRepository<SysResourceRole,Serializable>
{

}
