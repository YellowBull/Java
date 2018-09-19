
package com.jmev.cn.service.shiro.entiy;

import com.baomidou.mybatisplus.service.IService;
import com.jmev.cn.entity.shiro.SysConfigEntity;
import com.jmev.cn.util.PageUtils;

import java.util.Map;

/**
 * 系统配置信息
 */
public interface SysConfigService extends IService<SysConfigEntity>
{

	PageUtils queryPage(Map<String, Object> params);
	
	/**
	 * 保存配置信息
	 */
	public void save(SysConfigEntity config);
	
	/**
	 * 更新配置信息
	 */
	public void update(SysConfigEntity config);
	
	/**
	 * 根据key，更新value
	 */
	public void updateValueByKey(String key, String value);
	
	/**
	 * 删除配置信息
	 */
	public void deleteBatch(Long[] ids);
	
	/**
	 * 根据key，获取配置的value值
	 * 
	 * @param key           key
	 */
	public String getValue(String key);
	
	/**
	 * 根据key，获取value的Object对象
	 * @param key    key
	 * @param clazz  Object对象
	 */
	public <T> T getConfigObject(String key, Class<T> clazz);
	
}
