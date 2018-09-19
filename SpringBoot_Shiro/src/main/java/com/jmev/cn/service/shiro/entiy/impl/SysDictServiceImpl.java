
package com.jmev.cn.service.shiro.entiy.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jmev.cn.dao.shiro.SysDictDao;
import com.jmev.cn.entity.shiro.SysDictEntity;
import com.jmev.cn.service.shiro.entiy.SysDictService;
import com.jmev.cn.util.PageUtils;
import com.jmev.cn.util.Query;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("sysDictService")
public class SysDictServiceImpl extends ServiceImpl<SysDictDao, SysDictEntity> implements SysDictService
{

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String name = (String)params.get("name");

        Page<SysDictEntity> page = this.selectPage(
                new Query<SysDictEntity>(params).getPage(),
                new EntityWrapper<SysDictEntity>()
                    .like(StringUtils.isNotBlank(name),"name", name)
        );

        return new PageUtils(page);
    }

}
