package com.jmev.cn.dao.crud.dao.generator;

import java.io.Serializable;
import java.util.UUID;

import org.activiti.engine.impl.cfg.IdGenerator;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

/**
 * 自定义主键生成
 * @author 邵欣
 */
@Service
public class CustomIdentifierGenerator implements IdentifierGenerator,IdGenerator {
	
	private static String IP = DateTime.now().toString("yyyyMMddHHmmSS") + "-";

	/**
	 * Hibernate 自定义主键生成
	 */
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException
    {
        return id();
    }
	/**
	 * 按规则生成 id
	 * rule: IP + "-" + uuid
	 * @return
	 */
	public static String id(){
		return (new StringBuffer(IP)).append(UUID.randomUUID().toString()).toString();
	}

	@Override
	public String getNextId() {
		return id();
	}

	
	
	

}
