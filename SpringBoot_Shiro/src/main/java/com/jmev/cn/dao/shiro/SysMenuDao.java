
package com.jmev.cn.dao.shiro;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jmev.cn.entity.shiro.SysMenuEntity;

import java.util.List;

/**
 * 菜单管理
 */
public interface SysMenuDao extends BaseMapper<SysMenuEntity>
{
	
	/**
	 * 根据父菜单，查询子菜单
	 * @param parentId 父菜单ID
	 */
	List<SysMenuEntity> queryListParentId(Long parentId);
	
	/**
	 * 获取不包含按钮的菜单列表
	 */
	List<SysMenuEntity> queryNotButtonList();

}
