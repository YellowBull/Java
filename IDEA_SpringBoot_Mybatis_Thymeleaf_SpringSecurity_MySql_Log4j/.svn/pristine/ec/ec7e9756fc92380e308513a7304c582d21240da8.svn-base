package com.jmev.cn.service.basic;

import com.github.pagehelper.PageInfo;
import com.jmev.cn.dto.basic.QueryEvTboxDto;
import com.jmev.cn.entity.basic.EvTbox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("basicManagementService")
public class BasicManagementService
{
    @Autowired
    private EvTboxService evTboxService;

    @Autowired
    private BasicTboxViewService basicTboxViewService;// 视图

    public PageInfo<EvTbox> queryEvTbox(QueryEvTboxDto dto)
    {
        if ("1".equals(dto.getIsUse()))
        {
            return basicTboxViewService.queryEvTbox(dto);
        }
        return evTboxService.queryEvTbox(dto);
    }

    @Transactional(readOnly = false)
    public void addEvTbox(EvTbox evTbox)
    {
        evTboxService.addEvTbox(evTbox);
    }

    public boolean checkEvTboxUnique(EvTbox evTbox)
    {
        return evTboxService.checkEvTboxUnique(evTbox);
    }

    public void deleteEvTbox(Long id)
    {
        evTboxService.deleteEvTbox(id);
    }

    public List<EvTbox> exprotEvTbox(QueryEvTboxDto dto)
    {
        if ("1".equals(dto.getIsUse()))
        {
            return basicTboxViewService.exprotEvTbox(dto);
        }
        return evTboxService.exprotEvTbox(dto);
    }

    public void updateEvTbox(EvTbox evTbox)
    {
        evTboxService.updateEvTbox(evTbox);
    }

    public void addEvTboxsByExcel(List<EvTbox> eyTboxs)
    {
        evTboxService.addEvTboxsByExcel(eyTboxs);
    }

}
