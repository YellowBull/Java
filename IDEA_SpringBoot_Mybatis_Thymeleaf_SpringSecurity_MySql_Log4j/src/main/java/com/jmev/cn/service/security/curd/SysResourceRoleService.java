package com.jmev.cn.service.security.curd;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmev.cn.dao.security.SysResourceRoleDao;

@Transactional
@Service("sysResourceRoleService")
public class SysResourceRoleService
{
    @SuppressWarnings("unused")
    @Autowired
    private SysResourceRoleDao sysResourceRoleDao;
}
