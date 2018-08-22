package com.jmev.cn.service.security.curd;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.jmev.cn.dao.security.SysResourceDao;

@Transactional
@Service("sysResourceService")
public class SysResourceService
{
    @Resource(name = "sysResourceDao")
    private SysResourceDao sysResourceDao;

    public List<Map<String, Object>> findByRoleName(String auth)
    {
        return sysResourceDao.findByRoleName(auth);
    }
}
