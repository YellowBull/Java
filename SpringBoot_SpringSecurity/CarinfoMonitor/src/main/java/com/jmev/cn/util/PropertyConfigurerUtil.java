/**
 * 
 */
package com.jmev.cn.util;

import java.io.IOException;
import java.util.Properties;

import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * properties文件中的配置项
 * @author zhongrunfu
 *
 */
public class PropertyConfigurerUtil extends PropertySourcesPlaceholderConfigurer {
	
	private static Properties props; // 存取properties配置文件key-value结果
	
	@Override
	protected Properties mergeProperties() throws IOException {
		props  = super.mergeProperties();
		
		return props;
	}
	
	/**
	 * 获取key对应的value值
	 * @param key properties文件中的key
	 * @return key对应的value值
	 */
	public static String getProperty(String key) {
		return PropertyConfigurerUtil.props.getProperty(key);
	}

	/**
	 * 获取key对应的value值,若没有则返回指定的默认值
	 * @param key properties文件中的key
	 * @param defaultValue 默认值
	 * @return 返回key对应的value值,若没有则返回指定的默认值
	 */
	public static String getProperty(String key, String defaultValue) {
		return PropertyConfigurerUtil.props.getProperty(key, defaultValue);
	}

}
