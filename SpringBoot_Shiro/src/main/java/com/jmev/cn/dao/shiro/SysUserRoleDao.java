
package com.jmev.cn.dao.shiro;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jmev.cn.entity.shiro.SysUserRoleEntity;

import java.util.List;

/**
 * 用户与角色对应关系
 */
public interface SysUserRoleDao extends BaseMapper<SysUserRoleEntity>
{
	
	/**
	 * 根据用户ID，获取角色ID列表
	 */
	List<Long> queryRoleIdList(Long userId);

	/**
	 * 根据角色ID数组，批量删除
	 */
	int deleteBatch(Long[] roleIds);
}
