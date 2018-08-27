package com.jmev.cn.dao.basic;

import com.jmev.cn.dto.basic.QueryEvTboxDto;
import com.jmev.cn.entity.basic.EvTbox;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface EvTboxMapper {
    // 根据id删除
    int deleteByPrimaryKey(Long id);
    // 新增
    int insert(EvTbox record);
    // 判空新增
    int insertSelective(EvTbox record);
    // 根据id查询数据
    EvTbox selectByPrimaryKey(Long id);
    // 判空更新
    int updateByPrimaryKeySelective(EvTbox record);
    // 直接更新
    int updateByPrimaryKey(EvTbox record);
    // 查询是否存在
    BigInteger findByTboxNumOrIccidNum(EvTbox evTbox);
    // 按条件查询
    List<EvTbox> queryEvTbox(QueryEvTboxDto queryEvTboxDto);

    void addEvTboxsByExcel(List<EvTbox> eyTboxs);
}