# ExportExcelService 用于定义上传和导出Excel的处理方式的接口

```Java
package com.yutian.spring.service;

import java.io.InputStream;
import java.util.List;

public interface ExportExcelService<T> {
	public void exportExcel(List<T> carinfos,Class c,String path,String sheetName,String[] names)throws Exception;
	
	public <T> List<T> extract(InputStream is, @SuppressWarnings("rawtypes") Class clas);
}

```