package com.jmev.cn.service.security.curd;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmev.cn.dao.security.SysUserDao;
import com.jmev.cn.entity.security.SysUser;

@Transactional
@Service("sysUserService")
public class SysUserService
{
    @Autowired
    private SysUserDao sysUserDao;

    public SysUser findByName(String name)
    {
        return sysUserDao.findByName(name);
    }
}
