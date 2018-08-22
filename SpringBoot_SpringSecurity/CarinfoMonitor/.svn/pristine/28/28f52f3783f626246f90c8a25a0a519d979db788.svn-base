package com.jmev.cn.service.security.curd;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.jmev.cn.dao.crud.query.QueryRule;
import com.jmev.cn.dao.security.SysUserDao;
import com.jmev.cn.entity.security.SysUser;

@Transactional
@Service("sysUserService")
public class SysUserService
{
    @Resource
    private SysUserDao sysUserDao;

    public SysUser findByName(String name)
    {
        QueryRule queryRule = new QueryRule();
        queryRule.addEqual("name", name);
        return sysUserDao.findUniqueResult(queryRule, SysUser.class);
    }
}
