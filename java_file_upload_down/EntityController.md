# EntityController 用于调用Excel上传下载的控制器

```Java
package com.yutian.spring.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yutian.spring.entity.EyTbox;
import com.yutian.spring.service.ExportExcelService;
import com.yutian.spring.service.EyTboxService;

import net.sf.json.JSONObject;

/**
 * EyTboxController—— T-BOX管理
 * @author 邵欣
 *
 */
@Controller
@RequestMapping("/tbox/manager")
public class EyTboxController
{
    private static Logger logger = Logger.getLogger(EyTboxController.class);

    @Resource(name = "eyTboxService")
    private EyTboxService eyTboxService;
    @SuppressWarnings("rawtypes")
    @Resource(name = "exportExcelService")
    private ExportExcelService exportExcelService;

    /**
     * 分页查询 Excel导出EyTbox
     * 测试请求链接
     * http://localhost:8080/CarinfoMonitor1/tbox/manager/findEyTboxForPageList.mvc?page=1&rows=1&tboxFactory=-1&flowState=-1
     */
    @ResponseBody
    @SuppressWarnings("unchecked")
    @RequestMapping("/findEyTboxForPageList.mvc")
    public String findEyTboxForPageList(Integer page, Integer rows, String tboxNum, String iccidNum, String simNum,
            @RequestParam(defaultValue = "-1") Integer tboxFactory,
            @RequestParam(defaultValue = "-1") Integer flowState, @RequestParam(defaultValue = "0") String exportExcel,
            HttpServletRequest request, HttpSession session, HttpServletResponse response)
    {
        logger.info("/findEyTboxForPageList.mvc -------Start");
        JSONObject json = new JSONObject();
        List<EyTbox> list = null;
        try
        {
            list = eyTboxService.findEyTboxForPageList((page - 1) * rows, rows, tboxNum, iccidNum, simNum, tboxFactory,
                    flowState);
            json.put("total", eyTboxService.findEyTboxForInt(tboxNum, iccidNum, simNum, tboxFactory, flowState));
        } catch (Exception e)
        {
            json.put("flag", "-1");
            json.put("msg", "查询失败！");
            logger.error(e + "findEyTboxForPageList 查询出错");
            return json.toString();
        }

        if ("1".equals(exportExcel))// 判断是否导出
        {
            String path = request.getSession().getServletContext().getRealPath("/export");
            @SuppressWarnings("rawtypes")
            Class cl = null;
            try
            {
                cl = Class.forName("com.yutian.spring.entity.EyTbox");
                String filename = "eytbox.xls";
                exportExcelService.exportExcel(list, cl, path, filename, FieldName.EYTBOXNAMES);
                json.put("filename", filename);
            } catch (Exception e)
            {
                logger.error(e + "eytbox.xls 导出异常");
                json.put("msg", "eytbox.xls 导出异常");
                return json.toString();
            }
        }
        json.put("flag", "1");
        json.put("rows", list);
        json.put("msg", "查询成功！");
        logger.info("/findEyTboxForPageList.mvc -------End");
        return json.toString();
    }

}



    /**
     * excle 批量新增
     */
    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping("/addEyTboxsByExcel.mvc")
    public String addEyTboxsByExcel(@RequestParam("fileImport") MultipartFile file)
    {
        logger.info("/addEyTboxsByExcel.mvc -------Start");
        JSONObject json = new JSONObject();
        List<EyTbox> eyTBoxs = null;
        try
        {
            InputStream inputStream = file.getInputStream();
            eyTBoxs = exportExcelService.extract(inputStream, "com.yutian.spring.entity.EyTbox");
        } catch (IOException e)
        {
            json.put("flag", "-1");
            json.put("msg", "Excel 新增TBox失败！");
            logger.error(e + "Excel 新增TBox失败！");
            return json.toString();
        }

        try
        {
            String flag = eyTboxService.addEyTboxsByExcel(eyTBoxs);
            if ("-1".equals(flag))
            {
                json.put("flag", "-1");
                json.put("msg", "Excel 中信息未填加完整！");
                logger.error("Excel 中信息未填加完整！");
                return json.toString();
            }
            json.put("flag", "1");
            json.put("msg", "Excel 新增TBox成功！");
            logger.info("/addEyTboxsByExcel.mvc -------End");
            return json.toString();
        } catch (Exception e)
        {
            json.put("flag", "-1");
            json.put("msg", "Excel 新增TBox失败！");
            logger.error(e + "Excel 新增TBox失败！");
            return json.toString();
        }
    }
```