package com.jmev.cn.service.excel;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Repository("excelPortService")
public class ExcelPortService<T>
{
    private static Logger logger = LoggerFactory.getLogger(ExcelPortService.class);
    public static void exportExcel(String fileName, String title, String[] headers, List<Map> dataset)
    {
        SXSSFWorkbook workbook = new SXSSFWorkbook(100);
        // 标题
        Sheet sheet = workbook.createSheet(title);
        CellStyle style = workbook.createCellStyle();

        // 列名
        Row row = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++)
        {
            Cell cell = row.createCell(i);
            sheet.setColumnWidth(i, 5000);
            style.setAlignment(CellStyle.ALIGN_CENTER);
            cell.setCellValue(headers[i]);
        }

        Iterator<Map> it = dataset.iterator();
        int index = 0;
        while (it.hasNext())
        {
            index++;
            row = sheet.createRow(index);

            Map map = it.next();
            logger.info(map.toString());
            Set<String> mapKey = (Set<String>)map.keySet();
            logger.info(mapKey.toString());
            Iterator<String> iterator = mapKey.iterator();
            logger.info(iterator.toString());
            int num  = 0;
            while(iterator.hasNext()){
                Cell cell = row.createCell(num);
                num++;
                String key = iterator.next();
                logger.info(key);
                Object obj = map.get(key);
                if (obj instanceof Date)
                {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    cell.setCellValue(sdf.format(obj));
                } else if (obj instanceof Integer)
                {
                    cell.setCellValue((Integer) obj);
                } else if (obj instanceof Double)
                {
                    cell.setCellValue((Double) obj);
                } else
                {
                    cell.setCellValue((String) obj);
                }
            }
        }
        FileOutputStream fos;
        try
        {
            fos = new FileOutputStream(fileName);
            workbook.write(fos);
            fos.close();
        } catch (FileNotFoundException e)
        {
            logger.error("文件不存在");
            e.printStackTrace();
        } catch (IOException e)
        {
            logger.error("文件写入错误");

        }
    }
    public void exportExcel(List<T> ts, @SuppressWarnings("rawtypes") Class clas, String path, String filename,
            String[] names) throws Exception
    {
        SXSSFWorkbook wb = new SXSSFWorkbook(100);
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
        Sheet sheet = wb.createSheet(filename);
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
        Row row = sheet.createRow(0);
        // 第四步，创建单元格，并设置值表头 设置表头居中  
        CellStyle style = wb.createCellStyle();
        CellStyle style2 = wb.createCellStyle();
        style2.setFillForegroundColor(HSSFColor.WHITE.index);
        style2.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style2.setBorderBottom(CellStyle.BORDER_THIN);
        style2.setBorderLeft(CellStyle.BORDER_THIN);
        style2.setBorderRight(CellStyle.BORDER_THIN);
        style2.setBorderTop(CellStyle.BORDER_THIN);
        style2.setAlignment(CellStyle.ALIGN_CENTER);
        style2.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setAlignment(CellStyle.ALIGN_CENTER); // 创建一个居中格式 
        Field[] fields = clas.getDeclaredFields();
        for (int i = 0; i < names.length; i++)
        {
            Cell cell = row.createCell(i);
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
                Cell cell = row.createCell(i);
                cell.setCellStyle(style2);
                String fieldName = field.getName();
                String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                try
                {
                    @SuppressWarnings("rawtypes")
                    Class tCls = t.getClass();
                    @SuppressWarnings("unchecked")
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
            File file = new File(path, filename);
            FileOutputStream fout = new FileOutputStream(file);
            wb.write(fout);
            fout.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @SuppressWarnings(
    { "hiding", "unchecked" })
    public <T> List<T> extract(InputStream is, String forName)
    {
        List<T> ts = new ArrayList<>();
        try
        {
            XSSFWorkbook workbook = new XSSFWorkbook(is);
            SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook(workbook, 100);
            Sheet sheet = sxssfWorkbook.getSheetAt(0); //只读取第一个sheet进行处理
            for (int row_num = 1; row_num < (sheet.getLastRowNum() + 1); row_num++)
            { //处理当前sheet，循环读取每一行
                Row xss_row = sheet.getRow(row_num);
                Class<?> clazz = Class.forName(forName);
                Object object = clazz.newInstance();
                Field[] fields = clazz.getDeclaredFields();
                for (int i = 0; i < fields.length; i++)
                {
                    Field field = fields[i];
                    String fieldName = fields[i].getName();
                    String setMethodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                    Method setMethod = clazz.getMethod(setMethodName, new Class[]
                    { fields[i].getType() });// 创建出所有的set方法

                    if (field.getType().getSimpleName().equals("String"))
                    {
                        if (StringUtils.isBlank(xss_row.getCell(i).getStringCellValue()))
                        {
                            continue;
                        }
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