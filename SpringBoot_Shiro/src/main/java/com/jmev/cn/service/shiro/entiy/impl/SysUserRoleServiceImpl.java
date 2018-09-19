
package com.jmev.cn.service.shiro.entiy.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jmev.cn.dao.shiro.SysUserRoleDao;
import com.jmev.cn.entity.shiro.SysUserRoleEntity;
import com.jmev.cn.service.shiro.entiy.SysUserRoleService;
import com.jmev.cn.util.MapUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户与角色对应关系
 */
@Service("sysUserRoleService")
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleDao, SysUserRoleEntity> implements SysUserRoleService
{
	@Override
	public void saveOrUpdate(Long userId, List<Long> roleIdList) {
		//先删除用户与角色关系
		this.deleteByMap(new MapUtils().put("user_id", userId));

		if(roleIdList == null || roleIdList.size() == 0){
			return ;
		}
		
		//保存用户与角色关系
		List<SysUserRoleEntity> list = new ArrayList<>(roleIdList.size());
		for(Long roleId : roleIdList){
			SysUserRoleEntity sysUserRoleEntity = new SysUserRoleEntity();
			sysUserRoleEntity.setUserId(userId);
			sysUserRoleEntity.setRoleId(roleId);

			list.add(sysUserRoleEntity);
		}
		this.insertBatch(list);
	}

	@Override
	public List<Long> queryRoleIdList(Long userId) {
		return baseMapper.queryRoleIdList(userId);
	}

	@Override
	public int deleteBatch(Long[] roleIds){
		return baseMapper.deleteBatch(roleIds);
	}
}
