package com.jmev.cn.dao.security;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jmev.cn.entity.security.SysUser;

@Repository("sysUserDao")
public interface SysUserDao extends JpaRepository<SysUser,Serializable>
{

    @Query(" from SysUser where name = ?1")
    SysUser findByName(String name);

}
