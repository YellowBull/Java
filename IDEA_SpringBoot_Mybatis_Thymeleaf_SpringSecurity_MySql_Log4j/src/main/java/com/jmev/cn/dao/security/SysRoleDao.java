package com.jmev.cn.dao.security;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jmev.cn.entity.security.SysRole;

@Repository("sysRoleDao")
public interface SysRoleDao extends JpaRepository<SysRole,Serializable>
{
}
