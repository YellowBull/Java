# ExcelPortServiceImpl 实现Excel上传下载的处理方法

```Java
package com.yutian.spring.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.stereotype.Repository;

import com.yutian.spring.service.ExportExcelService;

@Repository("exportExcelService")
public class ExcelPortServiceImp<T> implements ExportExcelService<T>
{
    private static Logger logger = Logger.getLogger(ExcelPortServiceImp.class);

    // Excel 下载
    @Override
    public void exportExcel(List<T> ts, Class clas, String path, String sheetName, String[] names) throws Exception
    {
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
        HSSFSheet sheet = wb.createSheet(sheetName);
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
        HSSFRow row = sheet.createRow(0);
        // 第四步，创建单元格，并设置值表头 设置表头居中  
        HSSFCellStyle style = wb.createCellStyle();
        HSSFCellStyle style2 = wb.createCellStyle();
        style2.setFillForegroundColor(HSSFColor.WHITE.index);
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式 
        //        Class c = Class.forName("com.yutian.spring.entity.Carinfo");
        Field[] fields = clas.getDeclaredFields();
        //      String[] fieldNames = new String[fields.length];  
        for (int i = 0; i < names.length; i++)
        {
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(names[i]);
            cell.setCellStyle(style);
        }

        Iterator<T> it = ts.iterator();
        int index = 0;
        while (it.hasNext())
        {
            index++;
            row = sheet.createRow(index);
            T t = (T) it.next();
            // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
            for (short i = 0; i < fields.length; i++)
            {
                Field field = fields[i];
                HSSFCell cell = row.createCell(i);
                cell.setCellStyle(style2);
                String fieldName = field.getName();
                String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                try
                {
                    Class tCls = t.getClass();
                    Method getMethod = tCls.getMethod(getMethodName, new Class[]
                    {});
                    Object value = getMethod.invoke(t, new Object[]
                    {});
                    if (getMethodName.equals("getSinvol"))
                    {
                        if (value != null)
                        {
                            value = ((String) value).replaceAll(",", ", ");
                        }
                    }
                    if (getMethodName.equals("getTem"))
                    {
                        if (value != null)
                        {
                            value = ((String) value).replaceAll(",", ", ");
                        }
                    }
                    // 判断值的类型后进行强制类型转换
                    if (value instanceof Timestamp)
                    {
                        Date date = ((Timestamp) value).getTimestamp();
                        cell.setCellValue(date);
                    } else if (value instanceof Float)
                    {
                        Float f = (Float) value;
                        cell.setCellValue(f);
                    } else if (value instanceof String)
                    {
                        cell.setCellValue(value.toString());
                    } else
                    {
                        // 其它数据类型都当作字符串简单处理
                        if (value == null)
                        {
                            value = "";
                        }
                        cell.setCellValue(value.toString());
                    }

                } catch (Exception e)
                {
                    e.printStackTrace();
                } finally
                {
                    // 清理资源
                }
            }
        }

        try
        {
            File file = new File(path, sheetName);
            FileOutputStream fout = new FileOutputStream(file);
            wb.write(fout);
            fout.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    // Excel 上传解析
    @SuppressWarnings("deprecation")
    @Override
    public <T> List<T> extract(InputStream is, String forName)
    {
        List<T> ts = new ArrayList<>();
        try
        {
            // InputStream is = new FileInputStream(file_name);
            HSSFWorkbook workbook = new HSSFWorkbook(is);
            HSSFSheet sheet = workbook.getSheetAt(0); //只读取第一个sheet进行处理
            for (int row_num = 1; row_num < (sheet.getLastRowNum() + 1); row_num++)
            { //处理当前sheet，循环读取每一行
                HSSFRow xss_row = sheet.getRow(row_num);
                Class<?> clazz = Class.forName(forName);
                Object object = clazz.newInstance();
                Field[] fields = clazz.getDeclaredFields();
                // Class<?> tCls = (Class<?>) clazz.getClass();// 新建一个实体类
                for (int i = 0; i < fields.length; i++)
                {
                    Field field = fields[i];
                    String fieldName = fields[i].getName();
                    String setMethodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                    Method setMethod = clazz.getMethod(setMethodName, new Class[]
                    { fields[i].getType() });// 创建出所有的set方法

                    if (field.getType().getSimpleName().equals("String"))
                    {
                        setMethod.invoke(object, xss_row.getCell(i).getStringCellValue());
                    } else if (field.getType().getSimpleName().equals("Integer"))
                    {
                        if (StringUtils.isBlank(xss_row.getCell(i).getStringCellValue()))
                        {
                            continue;
                        }
                        setMethod.invoke(object, Integer.parseInt(xss_row.getCell(i).getStringCellValue()));
                    } else if (field.getType().getSimpleName().equals("Date"))
                    {
                        if (StringUtils.isBlank(xss_row.getCell(i).getStringCellValue()))
                        {
                            continue;
                        }
                        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
                        setMethod.invoke(object, sf.parse(xss_row.getCell(i).getStringCellValue()));
                    } else if (field.getType().getSimpleName().equals("Timestamp"))
                    {
                        if (StringUtils.isBlank(xss_row.getCell(i).getStringCellValue()))
                        {
                            continue;
                        }
                        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String time = sf.format(sf.parse(xss_row.getCell(i).getStringCellValue()));
                        setMethod.invoke(object, java.sql.Timestamp.valueOf(time));
                    }
                }
                ts.add((T) object);
            }
        } catch (Exception e)
        {
            logger.error(e + "解析文件异常");
        }
        return ts;
    }

}
```