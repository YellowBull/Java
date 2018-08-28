```Java
package com.jmev.cn.controller.security;

import com.jmev.cn.constant.CarinfoConstant;
import com.jmev.cn.entity.security.SysRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 角色控制类
 * @author 邵欣
 */
@Controller
@RequestMapping("/role")
public class RoleController
{

    private static Logger logger = LoggerFactory.getLogger(RoleController.class);
    private static String MSG = CarinfoConstant.MSG;

    // 添加角色
    @RequestMapping("/addRole")
    public ModelAndView addRole(ModelAndView model, SysRole role)
    {
        logger.info("----------addRole---------Start-------------");
        try
        {

            model.addObject(MSG, HttpStatus.OK);
        } catch (Exception e)
        {
            model.addObject(MSG, HttpStatus.EXPECTATION_FAILED);
            logger.error(e + "");
        }
        logger.info("----------addRole---------End-------------");
        return model;
    }

    // 删除角色
    @RequestMapping("/deleteRole")
    public ModelAndView deleteRole(ModelAndView model, String Id)
    {
        logger.info("----------deleteRole---------Start-------------");
        try
        {

            model.addObject(MSG, HttpStatus.OK);
        } catch (Exception e)
        {
            model.addObject(MSG, HttpStatus.EXPECTATION_FAILED);
            logger.error(e + "");
        }
        logger.info("----------deleteRole---------End-------------");
        return model;
    }

    // 修改角色
    @RequestMapping("/updateRole")
    public ModelAndView updateRole(ModelAndView model, SysRole role)
    {
        logger.info("----------updateRole---------Start-------------");
        try
        {

            model.addObject(MSG, HttpStatus.OK);
        } catch (Exception e)
        {
            model.addObject(MSG, HttpStatus.EXPECTATION_FAILED);
            logger.error(e + "");
        }
        logger.info("----------updateRole---------End-------------");
        return model;
    }

    // 分页查询角色
    @RequestMapping("/queryPageRole")
    public ModelAndView queryPageRole(ModelAndView model, SysRole role)
    {
        logger.info("----------queryPageRole---------Start-------------");
        try
        {

            model.addObject(MSG, HttpStatus.OK);
        } catch (Exception e)
        {
            model.addObject(MSG, HttpStatus.EXPECTATION_FAILED);
            logger.error(e + "");
        }
        logger.info("----------queryPageRole---------End-------------");
        return model;
    }

}

```