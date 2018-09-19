
package com.jmev.cn.service.shiro.entiy.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jmev.cn.dao.shiro.SysUserDao;
import com.jmev.cn.entity.shiro.SysDeptEntity;
import com.jmev.cn.entity.shiro.SysUserEntity;
import com.jmev.cn.service.shiro.ShiroUtils;
import com.jmev.cn.service.shiro.annotation.DataFilter;
import com.jmev.cn.service.shiro.entiy.SysDeptService;
import com.jmev.cn.service.shiro.entiy.SysUserRoleService;
import com.jmev.cn.service.shiro.entiy.SysUserService;
import com.jmev.cn.util.Constant;
import com.jmev.cn.util.PageUtils;
import com.jmev.cn.util.Query;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 系统用户
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUserEntity> implements SysUserService
{
	@Autowired
	private SysUserRoleService sysUserRoleService;
	@Autowired
	private SysDeptService sysDeptService;

	@Override
	public List<Long> queryAllMenuId(Long userId) {
		return baseMapper.queryAllMenuId(userId);
	}

	@Override
	@DataFilter(subDept = true, user = false)
	public PageUtils queryPage(Map<String, Object> params) {
		String username = (String)params.get("username");

		Page<SysUserEntity> page = this.selectPage(
			new Query<SysUserEntity>(params).getPage(),
			new EntityWrapper<SysUserEntity>()
				.like(StringUtils.isNotBlank(username),"username", username)
				.addFilterIfNeed(params.get(Constant.SQL_FILTER) != null, (String)params.get(Constant.SQL_FILTER))
		);

		for(SysUserEntity sysUserEntity : page.getRecords()){
			SysDeptEntity sysDeptEntity = sysDeptService.selectById(sysUserEntity.getDeptId());
			sysUserEntity.setDeptName(sysDeptEntity.getName());
		}

		return new PageUtils(page);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void save(SysUserEntity user) {
		user.setCreateTime(new Date());
		//sha256加密
		String salt = RandomStringUtils.randomAlphanumeric(20);
		user.setSalt(salt);
		user.setPassword(ShiroUtils.sha256(user.getPassword(), user.getSalt()));
		this.insert(user);
		
		//保存用户与角色关系
		sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(SysUserEntity user) {
		if(StringUtils.isBlank(user.getPassword())){
			user.setPassword(null);
		}else{
			user.setPassword(ShiroUtils.sha256(user.getPassword(), user.getSalt()));
		}
		this.updateById(user);
		
		//保存用户与角色关系
		sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
	}


	@Override
	public boolean updatePassword(Long userId, String password, String newPassword) {
        SysUserEntity userEntity = new SysUserEntity();
        userEntity.setPassword(newPassword);
        return this.update(userEntity,
                new EntityWrapper<SysUserEntity>().eq("user_id", userId).eq("password", password));
    }

}
