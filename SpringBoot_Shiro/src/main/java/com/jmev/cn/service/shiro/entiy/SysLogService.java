
package com.jmev.cn.service.shiro.entiy;

import com.baomidou.mybatisplus.service.IService;
import com.jmev.cn.entity.shiro.SysLogEntity;
import com.jmev.cn.util.PageUtils;

import java.util.Map;

/**
 * 系统日志
 * 
 */
public interface SysLogService extends IService<SysLogEntity>
{

    PageUtils queryPage(Map<String, Object> params);

}
