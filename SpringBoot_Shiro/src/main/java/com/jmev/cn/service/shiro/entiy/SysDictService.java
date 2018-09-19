
package com.jmev.cn.service.shiro.entiy;

import com.baomidou.mybatisplus.service.IService;
import com.jmev.cn.entity.shiro.SysDictEntity;
import com.jmev.cn.util.PageUtils;

import java.util.Map;

/**
 * 数据字典
 */
public interface SysDictService extends IService<SysDictEntity>
{

    PageUtils queryPage(Map<String, Object> params);
}

