
package com.jmev.cn.dao.shiro;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jmev.cn.entity.shiro.SysRoleMenuEntity;

import java.util.List;

/**
 * 角色与菜单对应关系
 */
public interface SysRoleMenuDao extends BaseMapper<SysRoleMenuEntity>
{
	
	/**
	 * 根据角色ID，获取菜单ID列表
	 */
	List<Long> queryMenuIdList(Long roleId);

	/**
	 * 根据角色ID数组，批量删除
	 */
	int deleteBatch(Long[] roleIds);
}
