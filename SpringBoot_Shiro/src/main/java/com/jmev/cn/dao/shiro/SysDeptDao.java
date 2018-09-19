
package com.jmev.cn.dao.shiro;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jmev.cn.entity.shiro.SysDeptEntity;

import java.util.List;

/**
 * 部门管理
 */
public interface SysDeptDao extends BaseMapper<SysDeptEntity>
{

    /**
     * 查询子部门ID列表
     * @param parentId  上级部门ID
     */
    List<Long> queryDetpIdList(Long parentId);

}
