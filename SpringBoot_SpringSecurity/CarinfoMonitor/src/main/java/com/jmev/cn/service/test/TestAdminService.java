package com.jmev.cn.service.test;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jmev.cn.constant.CarinfoConstant;
import com.jmev.cn.dao.crud.query.Page;
import com.jmev.cn.dao.crud.query.QueryRule;
import com.jmev.cn.dao.test.TestAdminDao;
import com.jmev.cn.entity.TestAdmin;

@Service("testAdminService")
public class TestAdminService
{
    @Resource(name ="testAdminDao")
    private TestAdminDao testAdminDao;
    
    @Transactional(readOnly = false)
    public void saveTestAdmin(TestAdmin testAdmin) {
        testAdminDao.save(testAdmin);
    }
    
    public Page queryPageOfTestAdmin() {
        QueryRule queryRule = QueryRule.getInstance(false);
        return testAdminDao.find(queryRule, CarinfoConstant.PAGE_INDEX, CarinfoConstant.PAGE_SIZE);
    }
}
