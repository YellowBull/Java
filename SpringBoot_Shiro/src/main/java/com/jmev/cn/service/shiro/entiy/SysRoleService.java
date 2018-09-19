
package com.jmev.cn.service.shiro.entiy;

import com.baomidou.mybatisplus.service.IService;
import com.jmev.cn.entity.shiro.SysRoleEntity;
import com.jmev.cn.util.PageUtils;

import java.util.Map;

/**
 * 角色
 */
public interface SysRoleService extends IService<SysRoleEntity>
{

	PageUtils queryPage(Map<String, Object> params);

	void save(SysRoleEntity role);

	void update(SysRoleEntity role);
	
	void deleteBatch(Long[] roleIds);

}
