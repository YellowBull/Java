package com.jmev.cn.util;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

/**
 * spring mvc data type convert
 *
 */
public final class FormString2DateConverter implements Converter<String, Date> {
	
	private static Logger log = LoggerFactory.getLogger(FormString2DateConverter.class);
	
	@Override
	public Date convert(String source) {
		
		return FormString2DateConverter.stringToDate(source);
	}
	
	// 支持转换的日期格式
	private static final String[] ACCEPT_DATE_FORMATS = {
		new String("yyyy-MM"),
		new String("yyyy-MM-dd"),
		new String("yyyy-MM-dd HH:mm"),
		new String("yyyy-MM-dd HH:mm:ss"),
		new String("yyyy/MM/dd")
	};
	
	public static Date stringToDate(String source){
		if(StringUtils.isBlank(source)){
			return null;
		}
		
		source = StringUtils.trim(source);
		
        if(source.matches("^\\d{4}-\\d{1,2}$")){
        	
            return parseDate(source, ACCEPT_DATE_FORMATS[0]);
            
        }else if(source.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$")){
        	
            return parseDate(source, ACCEPT_DATE_FORMATS[1]);
            
        }else if(source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}$")){
        	
            return parseDate(source, ACCEPT_DATE_FORMATS[2]);
            
        }else if(source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}$")){
        	
            return parseDate(source, ACCEPT_DATE_FORMATS[3]);
            
        }else if(source.matches("^\\d{4}/\\d{1,2}/\\d{1,2}$")){
        	
            return parseDate(source, ACCEPT_DATE_FORMATS[4]);
            
        } else if(source.matches("^[0-9]*$")) {
        	return long2Date(Long.parseLong(source)); 
        } else {
            throw new IllegalArgumentException("Invalid date value '" + source + "'");
        }
	}
	
	/**
     *
     * @param dateStr
     *            String 字符型日期
     * @param format
     *            String 格式
     * @return Date 日期
     */
    private static Date parseDate(String dateStr, String format) {
        
        try {
        	
        	DateTimeFormatter dtf = DateTimeFormat.forPattern(format);
            
            return dtf.parseDateTime(dateStr).toDate();
            
        } catch (Exception e) {
        	log.info("parseDate: {}", e);
        }
        
        return null;
    }
    
    /**
     * long -> date
     * @param l
     * @return
     */
    public static Date long2Date(Long l){
    	Calendar c = Calendar.getInstance();
		c.setTimeInMillis(l);
		return c.getTime();
    }
}
