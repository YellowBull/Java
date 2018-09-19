
package com.jmev.cn.service.shiro.entiy.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jmev.cn.dao.shiro.SysLogDao;
import com.jmev.cn.entity.shiro.SysLogEntity;
import com.jmev.cn.service.shiro.entiy.SysLogService;
import com.jmev.cn.util.PageUtils;
import com.jmev.cn.util.Query;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("sysLogService")
public class SysLogServiceImpl extends ServiceImpl<SysLogDao, SysLogEntity> implements SysLogService
{

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String key = (String)params.get("key");

        Page<SysLogEntity> page = this.selectPage(
            new Query<SysLogEntity>(params).getPage(),
            new EntityWrapper<SysLogEntity>().like(StringUtils.isNotBlank(key),"username", key)
        );

        return new PageUtils(page);
    }
}
