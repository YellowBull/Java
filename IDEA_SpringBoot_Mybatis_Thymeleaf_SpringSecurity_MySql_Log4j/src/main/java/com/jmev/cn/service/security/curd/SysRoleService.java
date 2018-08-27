package com.jmev.cn.service.security.curd;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmev.cn.dao.security.SysRoleDao;
import com.jmev.cn.entity.security.SysRole;

@Transactional
@Service("sysRoleService")
public class SysRoleService
{
    @Autowired
    private SysRoleDao sysRoleDao;

    public Iterable<SysRole> findAll()
    {
        return sysRoleDao.findAll();
    }
    
}
