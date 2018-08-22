package com.jmev.cn.service.security.curd;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.jmev.cn.dao.security.SysRoleDao;
import com.jmev.cn.entity.security.SysRole;

@Transactional
@Service("sysRoleService")
public class SysRoleService
{
    @Resource(name = "sysRoleDao")
    private SysRoleDao sysRoleDao;

    public List<SysRole> findAll()
    {
        return sysRoleDao.getAll();
    }
}
