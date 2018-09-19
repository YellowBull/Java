
package com.jmev.cn.service.redis;

import com.jmev.cn.entity.shiro.SysConfigEntity;
import com.jmev.cn.util.RedisKeys;
import com.jmev.cn.util.RedisUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 系统配置Redis
 */
@Component
public class SysConfigRedis
{
    @Resource
    private RedisUtils redisUtils;

    public void saveOrUpdate(SysConfigEntity config) {
        if(config == null){
            return ;
        }
        String key = RedisKeys.getSysConfigKey(config.getParamKey());
        redisUtils.set(key, config);
    }

    public void delete(String configKey) {
        String key = RedisKeys.getSysConfigKey(configKey);
        redisUtils.delete(key);
    }

    public SysConfigEntity get(String configKey){
        String key = RedisKeys.getSysConfigKey(configKey);
        return redisUtils.get(key, SysConfigEntity.class);
    }
}
