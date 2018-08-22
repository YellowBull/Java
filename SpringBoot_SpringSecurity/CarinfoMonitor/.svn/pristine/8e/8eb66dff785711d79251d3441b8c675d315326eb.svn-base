package com.jmev.cn.controller.test;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.jmev.cn.dao.crud.query.Page;
import com.jmev.cn.entity.TestAdmin;
import com.jmev.cn.service.test.TestAdminService;

@RestController
@RequestMapping("/testController")
public class TestController
{
    private Logger logger = Logger.getLogger(TestController.class);
    @Resource(name = "testAdminService")
    private TestAdminService testAdminService;

    @RequestMapping(value = "/greeting")
    @ResponseBody
    public ModelAndView greeting(ModelAndView mv,@RequestBody TestAdmin testAdmin)
    {
        logger.info("------------------greeting---------------Start-----------");
        try
        {
            testAdminService.saveTestAdmin(testAdmin);
            mv.setStatus(HttpStatus.OK);
            mv.setViewName("/test/addTestAdmin");
            mv.addObject("title", "欢迎使用Thymeleaf!");
            return mv;
        } catch (Exception e)
        {
            mv.addObject("msg", "请求失败/t/r" + e);
            logger.error("请求失败" + e);
        }
        logger.info("------------------greeting---------------End-----------");
        return mv;
    }
    
    @RequestMapping(value = "/queryPageOfTestAdmin")
    public ModelAndView queryPageOfTestAdmin(ModelAndView mv)
    {
        logger.info("------------------queryPageOfTestAdmin---------------Start-----------");
        try
        {
            Page page = testAdminService.queryPageOfTestAdmin();
            mv.setStatus(HttpStatus.OK);
            mv.setViewName("/test/pageTestAdmin");
            mv.addObject("page", page);
            return mv;
        } catch (Exception e)
        {
            mv.addObject("msg", "查询失败/t/r" + e);
            logger.error("查询失败" + e);
        }
        logger.info("------------------queryPageOfTestAdmin---------------End-----------");
        return mv;
    }
}
