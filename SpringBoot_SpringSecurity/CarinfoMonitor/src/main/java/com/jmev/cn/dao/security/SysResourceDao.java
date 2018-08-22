package com.jmev.cn.dao.security;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.jmev.cn.dao.crud.dao.support.GenericDaoSupport;
import com.jmev.cn.entity.security.SysResource;

@Repository("sysResourceDao")
public class SysResourceDao extends GenericDaoSupport<SysResource>
{

    public List<Map<String, Object>> findByRoleName(String auth)
    {
        StringBuffer sb = new StringBuffer();
        sb.append(" select resource_url from sys_resource sr, sys_resource_role srr, sys_role srl ");
        sb.append(" where sr.id = srr.resource_id and srr.role_id = srl.id ");
        sb.append(" and srl.name ='"+auth+"'");
        return this.findMapByNativeSQL(sb.toString(), new Object[]{});
    }

}
