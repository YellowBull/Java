package com.jmev.cn.dao.crud.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.jmev.cn.dao.crud.query.Page;
import com.jmev.cn.dao.crud.query.QueryRule;

/**
 * 泛型 定义 Dao 基本操作
 * @author 邵欣
 */
public interface GenericDao<T> {
	
	/**
	 * @return
	 */
	public Dao getDao();

	/**
	 * 持久化对象入数据库，无论TO处于何种状态，此方法都会重新持久化该对象。
	 * 
	 * @param entity
	 */
	public void save(T entity);

	public void save(T[] aentity);

	public void save(Collection<T> entities);

	/**
	 * 持久化对象，此方法同时涵盖了保存和更新操作。
	 * 
	 * 如果传入的对象是非持久化对象，此方法会将其持久化保存。 如果传入的对象已经是持久化对象，此方法会对其进行更新操作。
	 * 
	 * @param entity
	 */
	public void update(T entity);

	public void update(T[] aentity);

	public void update(Collection<T> entities);

	/**
	 * 删除指定的持久化对象，如果对象未被持久化则会抛出RuntimeTxception。
	 * 
	 * @param entity
	 */
	public void delete(T entity);

	public void delete(Collection<T> entities);

	/**
	 * 根据指定ID获取实体对象（对象类型由Dao的实现类提供），然后删除该持久化对象。
	 * 
	 * @param id
	 */
	public void deleteById(String id);

	/**
	 * 根据指定ID获取实体对象（对象类型由Dao的实现类提供），如果无记录则返回NULL。
	 * 
	 * @param id
	 * @return
	 */
	public T get(String id);

	/**
	 * 获取对象的所有数据集合（对象类型由Dao的实现类提供）。
	 * 
	 * @param clazz
	 * @return
	 */
	public List<T> getAll();

	/**
	 * 根据查询规则QueryRule，获取指定类型对象的数据集合
	 * 
	 * @param queryRule
	 * @return
	 */
	public List<T> find(QueryRule queryRule);

	/**
	 * 
	 * @param statement hql
	 * @param params
	 * @return
	 */
	public List<T> find(String statement, Map<String, Object> params);

	/**
	 * 根据查询规则QueryRule，以及页码，每页记录数，获取分页对象。
	 * 
	 * @param queryRule
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public Page find(QueryRule queryRule, int pageIndex, int pageSize);

	/**
	 * 
	 * @param statement hql
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public Page find(String statement, Map<String, Object> params, int pageIndex, int pageSize);

	/**
	 * 根据指定ID获取实体对象（对象类型由Dao的实现类提供），然后逻辑删除该持久化对象，并记录删除原因。
	 * 
	 * @param id
	 * @param delReason
	 */
	public void deleteById(String id, String delReason);

	/**
	 * 指定的缓冲对象进行清除
	 * @param entity
	 */
	public void evict(Object entity);
	
	/**
	 * 根据 sql和参数 values 查询唯一的结果, 注意捕获处理查询的结果不为一条的异常
	 * NonUniqueResultException,HibernateException
	 * @param sql
	 * @param values
	 * @return
	 */
	public Object findUniqueResultBySql(String sql, Object... values);
	
	/**
	 * 根据hql语句进行单值查询
	 * NonUniqueResultException,HibernateException
	 * @param hql
	 * @param values
	 * @return
	 */
	public Object findUniqueResult(String hql, Object... values);
	
	/**
	 * 根据 QueryRule 进行单值查询
	 * NonUniqueResultException,HibernateException
	 * @param <T>
	 * @param queryRule
	 * @param clazz
	 * @return
	 */
	public T findUniqueResult(QueryRule queryRule, Class<T> clazz);

}
