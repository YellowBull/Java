package com.jmev.cn.CarinfoMonitor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CarinfoMonitorApplicationTests {

	@Test
	public void contextLoads() {
	}

    public static  void Excel2007AboveOperate() throws IOException {
        
        XSSFWorkbook workbook1 = new XSSFWorkbook(new FileInputStream(new File("F:/evtbox.xlsx")));
        SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook(workbook1, 100);
//            Workbook workbook = WorkbookFactory.create(new FileInputStream(new File(filePath)));
        Sheet first = sxssfWorkbook.getSheetAt(0);
        for (int i = 0; i < 100000; i++) {
            Row row = first.createRow(i);
            for (int j = 0; j < 11; j++) {
                if(i == 0) {
                    // 首行
                    row.createCell(j).setCellValue("column" + j);
                } else {
                    // 数据
                    if (j == 0) {
                        CellUtil.createCell(row, j, String.valueOf(i));
                    } else
                        CellUtil.createCell(row, j, String.valueOf(Math.random()));
                }
            }
        }
        FileOutputStream out = new FileOutputStream("evtbox.xlsx");
        sxssfWorkbook.write(out);
        out.close();
    }

    public static void main(String[] args)
    {
        try
        {
            Excel2007AboveOperate();
        } catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
