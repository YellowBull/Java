package com.jmev.cn.dao.crud.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate5.HibernateTemplate;

import com.jmev.cn.dao.crud.query.Page;
import com.jmev.cn.dao.crud.query.QueryRule;

/**
 * 定义 Dao 基本操作
 * @author 邵欣
 */
public interface Dao {
	
	public HibernateTemplate getHibernateTemplate();

	/**
	 * 持久化对象入数据库，无论EO处于何种状态，此方法都会重新持久化该对象。
	 * 
	 * @param entity
	 */
	public void save(Object entity);

	public void save(Object[] aentity);

	public void save(Collection<?> entities);

	/**
	 * 持久化对象，此方法同时涵盖了保存和更新操作。
	 * 
	 * 如果传入的对象是非持久化对象，此方法会将其持久化保存。 如果传入的对象已经是持久化对象，此方法会对其进行更新操作。
	 * 
	 * @param entity
	 */
	public void update(Object entity);

	public void update(Object[] aentity);

	public void update(Collection<?> entities);

	/**
	 * 删除指定的持久化对象，如果对象未被持久化则会抛出RuntimeException。
	 * 
	 * @param entity
	 */
	public void delete(Object entity);

	public void delete(Collection<?> entities);

	/**
	 * 根据指定ID，获取指定类型的对象，然后删除该持久化对象。
	 * 
	 * @param clazz
	 * @param id
	 */
	public void deleteById(Class<?> clazz, Serializable id);

	/**
	 * 根据指定ID，获取指定类型的对象，并将其自动转型，如果无记录则返回NULL。 指定的对象类型由入参提供。
	 * 
	 * @param clazz
	 * @param id
	 * @return
	 */
	public <T> T get(Class<T> clazz, Serializable id);

	/**
	 * 获取指定类型对象的所有数据集合，并将其自动转型。指定的对象类型由入参提供。
	 * 
	 * @param clazz
	 * @return
	 */
	public <T> List<T> getAll(Class<T> clazz);

	/**
	 * 获取总记录数
	 * 
	 * @param statement hql
	 * @param params
	 * @return
	 */
	public Long getCount(String statement, Map<String, Object> params);

	/**
	 * 根据传入的查询语句，以及过滤参数与值绑定的Map对象，进行数据访问。
	 * 
	 * @param statement hql
	 * @param params
	 * @return
	 */
	public List<?> find(String statement, Map<String, Object> params);

	/**
	 * 根据传入的查询语句，过滤参数与值绑定的Map对象，以及页码，每页记录数，获取分页对象。
	 * 
	 * @param statement hql
	 * @param params
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public Page find(String statement, Map<String, Object> params, int pageIndex, int pageSize);

	/**
	 * 获取总记录数
	 * 
	 * @param sql
	 * @param values
	 * @return
	 */
	public Long getCountByNativeSQL(String sql, Object... values);

	/**
	 * 根据传入的数据库原生SQL语句，以及查询变量所对应的参数值，进行数据访问。
	 * 
	 * @param sql
	 * @param values
	 * @return
	 */
	public List<Object[]> findByNativeSQL(String sql, Object... values);

	/**
	 * 根据传入的数据库原生SQL语句，以及查询变量所对应的参数值，进行数据访问，原生查询通过一个SQL语句和隐式的映像到一个实体。
	 * 
	 * 映射元数据为基础的一个实体，它认为原生查询的结果集中的栏将完全匹配实体的O/R映射。 原生SQL查询的映射的实体通过clazz参数来确定。
	 * 
	 * @param sql
	 * @param clazz
	 * @param values
	 * @return
	 */
	public <T> List<T> findByNativeSQL(String sql, Class<T> clazz, Object... values);

	/**
	 * 
	 * @param sql
	 * @param values
	 * @return
	 */
	public List<Map<String, Object>> findMapByNativeSQL(String sql, Object... values);

	/**
	 * 根据传入的数据库原生SQL语句，查询变量所对应的参数值，以及页码，每页记录数，及获取分页对象。
	 * 
	 * @param sql
	 * @param clazz
	 * @param pageIndex
	 * @param pageSize
	 * @param values
	 * @return
	 */
	public Page findByNativeSQL(String sql, Class<?> clazz, int pageIndex, int pageSize, Object... values);

	/**
	 * 
	 * @param sql
	 * @param pageIndex
	 * @param pageSize
	 * @param values
	 * @return
	 */
	public Page findByNativeSQL(String sql, int pageIndex, int pageSize, Object... values);

	/**
	 * 
	 * @param sql
	 * @param pageIndex
	 * @param pageSize
	 * @param values
	 * @return
	 */
	public Page findMapByNativeSQL(String sql, int pageIndex, int pageSize, Object... values);
	/**
	 * 特殊的自定义sql查询
	 * @param sql
	 * @param pageIndex
	 * @param pageSize
	 * @param values
	 * @return
	 */
	public Page findMapByNativeSpecialSQL(String sql, int pageIndex, int pageSize, Object... values);

	/**
	 * 根据sql语句进行单值查询
	 * 
	 * @param sql
	 * @param values
	 * @return
	 */
	public Object findObjectByNativeSQL(String sql, Object... values);
	
	/**
	 * 根据hql语句进行单值查询
	 * @param hql
	 * @param values
	 * @return
	 */
	public Object findUniqueResult(final String hql, final Object... values);
	
	/**
	 * 根据 QueryRule 进行单值查询
	 * @param <T>
	 * @param queryRule
	 * @param clazz
	 * @return
	 */
	public <T> T findUniqueResult(final QueryRule queryRule, final Class<T> clazz);

	/**
	 * Flush all pending saves, updates and deletes to the database.
	 */
	public void flush();

	/**
	 * Remove all objects from the Session cache, and cancel all pending saves, updates and deletes.
	 */
	public void clear();

	/**
	 * Remove the given object from the Session cache.
	 * 
	 * @param entity
	 */
	public void evict(Object entity);

	/**
	 * Check whether the given object is in the Session cache.
	 * 
	 * @param entity
	 * @return
	 */
	public boolean contains(Object entity);

	/**
	 * 根据查询规则QueryRule，获取数据集合，查询的结果集中映射的实体通过clazz参数来确定。
	 * 
	 * @param queryRule
	 * @param clazz
	 * @return
	 */
	public <T> List<T> find(QueryRule queryRule, Class<T> clazz);

	/**
	 * 根据查询规则QueryRule，以及页码，每页记录数，获取分页对象。
	 * 
	 * @param queryRule
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page find(QueryRule queryRule, Class<?> clazz, int pageIndex, int pageSize);

	/**
	 * 使用HQL的delete和update语句进行更新
	 * 
	 * @param hql
	 * @param values
	 * @return
	 */
	public int executeUpdate(String hql, Object... values);

	/**
	 * 使用SQL的delete和update语句进行更新
	 * 
	 * @param sql
	 * @param values
	 * @return
	 */
	public int executeUpdateByNativeSQL(String sql, Object... values);
	
	/**
	 * 执行无返回结果的存储过程
	 * @param pd 存储过程
	 * @param values 参数
	 * @return void
	 */
	public void executeVoidProcedure(String pd, Object... values);

	/**
	 * 通过存储过程查询(单结果集)
	 * @param pd
	 * @param values
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> executeSingletonProcedure(String pd, Object... values);

	/**
	 * 执行含返回参数的存储过程
	 * @param @param pd
	 * @param @param clazz
	 * @param @param outParam
	 * @param @param outParamType
	 * @param @param ins
	 * @param @return
	 * @return Map<String,T>
	 */
	public Map<String, Object> executeOutParamProcedure(String pd, String outParam, Integer outParamType, Object... ins);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
