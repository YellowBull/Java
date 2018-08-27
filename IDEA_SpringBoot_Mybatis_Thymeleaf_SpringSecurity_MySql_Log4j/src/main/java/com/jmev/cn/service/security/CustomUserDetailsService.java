package com.jmev.cn.service.security;

import com.jmev.cn.entity.security.SecurityUser;
import com.jmev.cn.entity.security.SysUser;
import com.jmev.cn.service.security.curd.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService
{
    public static Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
    @Autowired
    private SysUserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        // SysUser对应数据库中的用户表，是最终存储用户和密码的表，可自定义
        // 本例使用SysUser中的name作为用户名:
        SysUser sysUser = sysUserService.findByName(username);
        if (sysUser == null)
        {
            logger.error("UserName " + username + " not found");
        }
        // SecurityUser实现UserDetails并将SysUser的name映射为username
        SecurityUser seu = new SecurityUser(sysUser);
        return seu;
    }

}
