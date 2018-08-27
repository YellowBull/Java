package com.jmev.cn.dao.security;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jmev.cn.entity.security.SysResource;

@Repository("sysResourceDao")
public interface SysResourceDao extends JpaRepository<SysResource,Serializable>
{
    @Query("select sr.resourceUrl from SysResource sr, SysResourceRole srr, SysRole srl where sr.id = srr.resourceId "
            + " and srl.id = srr.roleId  and  srl.name = ?1")
    List<String> findByRoleName(String auth);

}
