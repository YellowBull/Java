package com.jmev.cn.dao.crud;

import java.beans.Introspector;
import java.io.Serializable;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;

import eu.medsea.mimeutil.MimeUtil;
/**
 * @author 邵欣
 */
@Service("spring.springBeanService")
public class SpringBeanService implements ApplicationContextAware,Serializable {

	private static final long serialVersionUID = 5679269338390603558L;
	
	private static ApplicationContext context = null;
	
	/* 
	 * (non-Javadoc)
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringBeanService.context = applicationContext;
		/**
		 * 注册文件类型探查器
		 */
		MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");
	}
	
	public static Object getBean(String beanId) {
		return context.getBean(beanId);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<T> clazz, String beanId) {
		return (T) context.getBean(beanId);
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<T> clazz) {
		String beanName = ClassUtils.getShortName(clazz.getName());
		beanName = Introspector.decapitalize(beanName);
		return (T) context.getBean(beanName);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getBeans(Class<T> clazz) {
		String beanName = ClassUtils.getShortName(clazz.getName());
		beanName = Introspector.decapitalize(beanName);
		return (T) context.getBean(beanName);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getBeans(Class<T> clazz, String beanId) {
		return (T) context.getBean(beanId);
	}

}