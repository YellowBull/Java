package com.jmev.cn.service.security.curd;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jmev.cn.dao.security.SysResourceDao;

@Transactional
@Service("sysResourceService")
public class SysResourceService
{
    @Autowired
    private SysResourceDao sysResourceDao;
    public List<String> findByRoleName(String auth)
    {
        return sysResourceDao.findByRoleName(auth);
    }
}
