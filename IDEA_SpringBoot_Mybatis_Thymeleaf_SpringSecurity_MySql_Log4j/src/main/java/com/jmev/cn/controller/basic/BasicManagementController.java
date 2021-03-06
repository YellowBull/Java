package com.jmev.cn.controller.basic;

import com.github.pagehelper.PageInfo;
import com.jmev.cn.constant.CarinfoConstant;
import com.jmev.cn.dto.basic.EvTboxImport;
import com.jmev.cn.dto.basic.QueryEvTboxDto;
import com.jmev.cn.entity.basic.EvTbox;
import com.jmev.cn.service.basic.BasicManagementService;
import com.jmev.cn.service.excel.ExcelPortService;
import com.jmev.cn.service.unicomAPI.UnicomAPIService;
import com.jmev.cn.util.GetNineOfMonth;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/basic")
public class BasicManagementController
{
    private static Logger logger = LoggerFactory.getLogger(BasicManagementController.class);
    @Resource
    private BasicManagementService basicManagementService;
    @Resource
    private UnicomAPIService unicomAPIService;
    @Resource
    private ExcelPortService exportExcelService;

    /**
     * 终端管理
     */
    // 查询
    @RequestMapping(value = "/queryEvTbox", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView queryEvTbox(ModelAndView model, @RequestBody QueryEvTboxDto queryEvTboxDto)
    {
        logger.info("----------queryEvTbox---------Start-------------");
        try
        {
            PageInfo<EvTbox> page = basicManagementService.queryEvTbox(queryEvTboxDto);
            model.addObject("msg", "查询evtbox 成功");
            model.addObject("page", page);
            model.setStatus(HttpStatus.OK);
            model.setViewName("/test/page.html");// TODO: pageName
        } catch (Exception e)
        {
            model.setStatus(HttpStatus.EXPECTATION_FAILED);
            model.addObject("msg", e + " 查询evtbox 异常");
            logger.error(e + " 查询evtbox 异常");
            return model;
        }
        logger.info("----------queryEvTbox---------End-------------");
        return model;
    }

    // 批量导出
    @RequestMapping(value = "/exprotEvTbox", method = RequestMethod.POST)
    public ModelAndView exprotEvTbox(ModelAndView model, @RequestBody QueryEvTboxDto queryEvTboxDto,
            HttpServletRequest request)
    {
        logger.info("----------exprotEvTbox---------Start-------------");
        try
        {
            List<EvTbox> evTboxs = basicManagementService.exprotEvTbox(queryEvTboxDto);
            String path = request.getSession().getServletContext().getRealPath("");
            Class<?> cl = Class.forName("com.jmev.cn.entity.basic.EvTbox");
            String filename = "evtbox" + (new Date()).getTime() + ".xlsx";// 防止同时导出文件名冲突
            exportExcelService.exportExcel(evTboxs, cl, path, filename, CarinfoConstant.EVTBOXNAMES);// TODO: 导出具体字段
            model.addObject("msg", "查询evtbox 成功");
            model.addObject("filename", filename);
            model.setViewName("table.html");// TODO: pageName
            model.setStatus(HttpStatus.OK);
        } catch (Exception e)
        {
            model.addObject("msg", e + " 查询evtbox 异常");
            logger.error(e + " 查询evtbox 异常");
            model.setStatus(HttpStatus.EXPECTATION_FAILED);
            return model;
        }
        logger.info("----------exprotEvTbox---------End-------------");
        return model;
    }

    // 新增单条
    @RequestMapping(value = "/addEvTbox", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView addEvTbox(ModelAndView model, @RequestBody EvTbox evTbox)
    {
        logger.info("----------addEvTbox---------Start-------------");
        try
        {
            boolean flag = basicManagementService.checkEvTboxUnique(evTbox);
            String iccidNum = evTbox.getIccidNum();
            if (flag)
            {
                model.addObject("msg", "校验evtbox唯一性异常");
                logger.error("校验evtbox唯一性异常");
                model.setStatus(HttpStatus.EXPECTATION_FAILED);
                return model;
            }
            if (iccidNum != null && iccidNum.length() > 17 && "8986011".equals(iccidNum.substring(0, 7)))
            {

                Map<String, LinkedList<String>> getTerminalDetails;
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                Date activatedDateFormat;
                try
                {
                    ArrayList<String> iccids = new ArrayList<>();
                    iccids.add(evTbox.getIccidNum());
                    getTerminalDetails = unicomAPIService.GetTerminalDetails(iccids);
                } catch (Exception e)
                {
                    logger.error(e + "Unicom API Error");
                    model.addObject("msg", e + "Unicom API Error");
                    model.setStatus(HttpStatus.EXPECTATION_FAILED);
                    return model;
                }
                List<String> dateActivated = getTerminalDetails.get("dateActivated");
                List<String> status = getTerminalDetails.get("status");
                try
                {
                    activatedDateFormat = sf.parse(dateActivated.get(0));
                    evTbox.setActivatedDate(activatedDateFormat);
                } catch (ParseException e)
                {
                    logger.error(e + "日期转换异常");
                    model.addObject("msg", e + "日期转换异常");
                    model.setStatus(HttpStatus.EXPECTATION_FAILED);
                    return model;
                }

                String statu = status.get(0);
                if ("TEST_READY_NAME".equals(statu))
                {
                    evTbox.setFlowState(1);
                }
                if ("INVENTORY_NAME".equals(statu))
                {
                    evTbox.setFlowState(2);
                }
                if ("ACTIVATION_READY_NAME".equals(statu))
                {
                    evTbox.setFlowState(5);
                }
                if ("ACTIVATED_NAME".equals(statu))
                {
                    evTbox.setFlowState(3);
                }
                if ("DEACTIVATED_NAME".equals(statu))
                {
                    evTbox.setFlowState(4);
                }
                if ("RETIRED_NAME".equals(statu))
                {
                    evTbox.setFlowState(5);
                }
                if ("PURGED_NAME".equals(statu))
                {
                    evTbox.setFlowState(5);
                }
            }
            evTbox.setCreatedTime(new Date());
            evTbox.setVersion(1);
            evTbox.setLastAccess(new Date());
            basicManagementService.addEvTbox(evTbox);
            model.addObject("msg", "新增单条evtbox 成功");
            model.setViewName("/test/page.html");// TODO: pageName
            model.setStatus(HttpStatus.OK);
        } catch (Exception e)
        {
            model.addObject("msg", e + " 新增单条evtbox 异常");
            logger.error(e + " 新增单条evtbox 异常");
            model.setStatus(HttpStatus.EXPECTATION_FAILED);
            return model;
        }
        logger.info("----------addEvTbox---------End-------------");
        return model;
    }

    // excle 批量新增
    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping("/addEvTboxsByExcel")
    public ModelAndView addEvTboxsByExcel(@RequestParam("fileImport") MultipartFile file, ModelAndView model)
    {
        logger.info("/addEvTboxsByExcel.mvc -------Start");
        List<EvTboxImport> evTboxsImports;
        try
        {
            evTboxsImports = exportExcelService
                    .extract(file.getInputStream(), "com.jmev.cn.dto.basic.EvTboxImport");// 解析excel
        } catch (IOException e)
        {
            model.setStatus(HttpStatus.EXPECTATION_FAILED);
            model.addObject("msg", "Excel 新增TBox失败！");
            logger.error(e + "Excel 新增TBox失败！");
            return model;
        }

        // 根据iccid添加激活状态和激活时间
        ArrayList<String> iccids = new ArrayList<>();
        for (EvTboxImport evTboxsImport : evTboxsImports)
        {
            if (evTboxsImport.getIccidNum() == null)
            {
                model.setStatus(HttpStatus.EXPECTATION_FAILED);
                model.addObject("msg", "Excel 中iccid_num信息未填加完整！");
                logger.error("Excel 中iccid_num信息未填加完整！");
                return model;
            }
            iccids.add(evTboxsImport.getIccidNum());
        }
        Map<String, LinkedList<String>> getTerminalDetails = null;
        List<String> dateActivated = new ArrayList<>();
        List<String> status = new ArrayList<>();
        List<EvTbox> evTboxs = new ArrayList<>();
        try
        {
            int temp = 0;
            ArrayList<String> iccidsOnce = new ArrayList<>();
            for (String iccid : iccids)
            {
                iccidsOnce.add(iccid);
                temp++;
                if (temp % 50 == 0)
                {
                    getTerminalDetails = unicomAPIService.GetTerminalDetails(iccidsOnce);
                    dateActivated.addAll(getTerminalDetails.get("dateActivated"));
                    status.addAll(getTerminalDetails.get("status"));
                    iccidsOnce = new ArrayList<>();// 重置集合
                }
            }
            if (iccidsOnce.size() > 0)
            {
                getTerminalDetails = unicomAPIService.GetTerminalDetails(iccidsOnce);
                dateActivated.addAll(getTerminalDetails.get("dateActivated"));
                status.addAll(getTerminalDetails.get("status"));
            }
            for (int i = 0; i < evTboxsImports.size(); i++)
            {
                EvTbox evTbox = new EvTbox();
                evTbox.setActivatedDate(DateUtils.parseDate(dateActivated.get(i)));
                String statu = status.get(i);
                if ("TEST_READY_NAME".equals(statu))
                {
                    evTbox.setFlowState(1);
                }
                if ("INVENTORY_NAME".equals(statu))
                {
                    evTbox.setFlowState(2);
                }
                if ("ACTIVATION_READY_NAME".equals(statu))
                {
                    evTbox.setFlowState(5);
                }
                if ("ACTIVATED_NAME".equals(statu))
                {
                    evTbox.setFlowState(3);
                }
                if ("DEACTIVATED_NAME".equals(statu))
                {
                    evTbox.setFlowState(4);
                }
                if ("RETIRED_NAME".equals(statu))
                {
                    evTbox.setFlowState(5);
                }
                if ("PURGED_NAME".equals(statu))
                {
                    evTbox.setFlowState(5);
                }
                if (evTboxsImports.get(i).getTboxNum() == null)
                {
                    model.setStatus(HttpStatus.EXPECTATION_FAILED);
                    model.addObject("msg", "Excel 中tbox_num信息未填加完整！");
                    logger.error("Excel 中tbox_num信息未填加完整！");
                    return model;
                }
                evTbox.setTboxNum(evTboxsImports.get(i).getTboxNum());
                evTbox.setIccidNum(evTboxsImports.get(i).getIccidNum());
                evTbox.setBluetoothMac(evTboxsImports.get(i).getBluetoothMac());
                evTbox.setTboxFactory(evTboxsImports.get(i).getTboxFactory());
                evTbox.setSimNum(evTboxsImports.get(i).getSimNum());
                evTbox.setTboxModel(evTboxsImports.get(i).getTboxModel());
                evTbox.setSupplyDate(evTboxsImports.get(i).getSupplyDate());
                evTbox.setVersion(1);
                evTbox.setChargeDate(evTboxsImports.get(i).getChargeDate());
                evTbox.setCreatedTime(new Date());
                evTbox.setLastAccess(new Date());
                evTboxs.add(evTbox);
            }
        } catch (Exception e)
        {
            logger.error(e + " Unicom API Error");
            model.setStatus(HttpStatus.EXPECTATION_FAILED);
            return model;
        }

        try
        {
            basicManagementService.addEvTboxsByExcel(evTboxs);// 保存
            model.setStatus(HttpStatus.OK);
            model.addObject("msg", "Excel 新增TBox成功！");
            logger.info("/addEvTboxsByExcel.mvc -------End");
            return model;
        } catch (Exception e)
        {
            model.setStatus(HttpStatus.EXPECTATION_FAILED);
            model.addObject("msg", "Excel 新增TBox失败！");
            logger.error(e + "Excel 新增TBox失败！");
            return model;
        }
    }

    // 删除单条
    @RequestMapping(value = "/deleteEvTbox",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView deleteEvTbox(ModelAndView model, Long id)
    {
        logger.info("----------deleteEvTbox---------Start-------------");
        try
        {
            basicManagementService.deleteEvTbox(id);
            model.addObject("msg", " 删除单条evtbox 成功");
            model.setStatus(HttpStatus.OK);
        } catch (Exception e)
        {
            model.addObject("msg", e + "  删除单条evtbox 异常");
            logger.error(e + "  删除单条evtbox 异常");
            model.setStatus(HttpStatus.EXPECTATION_FAILED);
            return model;
        }
        logger.info("----------deleteEvTbox---------End-------------");
        return model;
    }

    // 修改单条数据
    @RequestMapping(value = "/updateEvTbox",method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView updateEvTbox(ModelAndView model, @RequestBody EvTbox evTbox)
    {
        logger.info("----------updateEvTbox---------Start-------------");
        try
        {
            boolean flag = basicManagementService.checkEvTboxUnique(evTbox);
            if (flag)
            {
                model.addObject("msg", "校验evtbox唯一性异常");
                logger.error("校验evtbox唯一性异常");
                model.setStatus(HttpStatus.EXPECTATION_FAILED);
                return model;
            }
            evTbox.setLastAccess(new Date());
            evTbox.setVersion(evTbox.getVersion() + 1);
            basicManagementService.updateEvTbox(evTbox);
            model.addObject("msg", " 修改单条evtbox 成功");
            model.setViewName("/test/page.html");// TODO: pageName
            model.setStatus(HttpStatus.OK);
        } catch (Exception e)
        {
            model.addObject("msg", e + "  修改单条evtbox 异常");
            logger.error(e + "  修改单条evtbox 异常");
            model.setStatus(HttpStatus.EXPECTATION_FAILED);
            return model;
        }
        logger.info("----------updateEvTbox---------End-------------");
        return model;
    }

    // 获取单个iccid10个月的用量
    @ResponseBody
    @RequestMapping(value = "/getUseage.mvc",method = RequestMethod.GET)
    public ModelAndView getUseage(String iccid, ModelAndView model)
    {
        logger.info("/getUseage.mvc -------Start");
        if (iccid == null || iccid.length() < 17 || !"8986011".equals(iccid.substring(0, 7)))
        {
            model.setStatus(HttpStatus.EXPECTATION_FAILED);
            model.addObject("msg", "查询失败！");
            logger.error("iccid存在问题查询失败！");
            return model;
        }
        List<String> month = GetNineOfMonth.getMonth();
        List<String> iccids = new ArrayList<>();
        List<String> useages = new ArrayList<>();
        iccids.add(iccid);
        Map<String, LinkedList<String>> getTerminalDetails;
        try
        {
            getTerminalDetails = unicomAPIService.GetTerminalDetails(iccids);
            useages.add(getTerminalDetails.get("useage").get(0));
            for (String time : month)
            {
                useages.add(unicomAPIService.GetTerminalUsageRequest(iccid, time));
            }
        } catch (Exception e)
        {
            model.setStatus(HttpStatus.EXPECTATION_FAILED);
            model.addObject("msg", "查询失败！");
            logger.error(e + "查询失败！");
            return model;
        }
        month.add(0, GetNineOfMonth.getCurrentMonth());
        model.setStatus(HttpStatus.OK);
        model.addObject("useages", useages);
        model.addObject("month", month);
        model.addObject("msg", "查询成功！");
        logger.info("/getUseage.mvc -------End");
        return model;
    }
}
