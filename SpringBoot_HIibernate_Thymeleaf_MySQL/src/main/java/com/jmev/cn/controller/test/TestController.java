package com.jmev.cn.controller.test;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.jmev.cn.entity.TestAdmin;
import com.jmev.cn.service.test.TestAdminService;

@RestController
@RequestMapping("/testController")
public class TestController
{
    @Resource(name="testAdminService")
    private TestAdminService testAdminService;
    
    @RequestMapping(value = "/greeting")
    public ModelAndView test(ModelAndView mv,String username,String password) {
        TestAdmin testAdmin = new TestAdmin();
        testAdmin.setUsername(username);
        testAdmin.setPassworld(password);
        testAdminService.saveTestAdmin(testAdmin);
        mv.setViewName("/test/greeting");
        mv.addObject("title","欢迎使用Thymeleaf!");
        return mv;
    }
}
