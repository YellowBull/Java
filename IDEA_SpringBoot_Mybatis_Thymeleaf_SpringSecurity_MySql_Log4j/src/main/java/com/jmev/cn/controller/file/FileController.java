package com.jmev.cn.controller.file;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 文件导出通用控制类
 * @author 邵欣
 *
 */
@Controller
@RequestMapping("/fileController")
public class FileController
{
    @RequestMapping("/download")
    public ResponseEntity<byte[]> downloadExcel(HttpServletRequest request, String filename) throws IOException
    {
        String path = request.getSession().getServletContext().getRealPath("");
        System.out.println(path);
        File file = new File(path, filename);
        HttpHeaders headers = new HttpHeaders();
        String fileName = new String(filename.getBytes("UTF-8"), "iso-8859-1");//为了解决中文名称乱码问题  
        headers.setContentDispositionFormData("attachment", fileName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.OK);
    }
}
