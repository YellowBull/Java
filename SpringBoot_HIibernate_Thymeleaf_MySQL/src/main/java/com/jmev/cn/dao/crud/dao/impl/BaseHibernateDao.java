package com.jmev.cn.dao.crud.dao.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.internal.CriteriaImpl.OrderEntry;
import org.hibernate.jdbc.Work;
import org.springframework.context.annotation.ImportResource;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jmev.cn.dao.crud.dao.Dao;
import com.jmev.cn.dao.crud.dao.support.QueryRuleSupport;
import com.jmev.cn.dao.crud.query.Page;
import com.jmev.cn.dao.crud.query.QueryRule;

/**
 * dao基本操作封装
 * @author 邵欣 
 */
@SuppressWarnings(
{ "rawtypes", "unchecked", "deprecation" })
@Component("baseHibernateDao")
@ImportResource("classpath:/config/core/spring-hibernate.xml")
public class BaseHibernateDao extends HibernateDaoSupport implements Dao
{
    @Resource
    public SessionFactory sessionFactory;
    
    /**
     * 保存数据
     */
    @Override
    public void save(Object entity)
    {
        getHibernateTemplate().save(entity);
    }

    /**
     * 批量保存数据
     */
    @Override
    public void save(Object[] aentity)
    {
        if (aentity != null && aentity.length > 0)
        {
            for (int i = 0; i < aentity.length; i++)
            {
                save(aentity[i]);
            }
        }
    }

    /**
     * 批量保存数据
     */
    @Override
    public void save(Collection<?> entities)
    {
        if (entities != null && !entities.isEmpty())
        {
            for (Object entity : entities)
            {
                save(entity);
            }
        }
    }

    /**
     * 更新数据
     */
    @Override
    public void update(Object entity)
    {
        getHibernateTemplate().saveOrUpdate(entity);
    }

    /**
     * 批量更新数据
     */
    @Override
    public void update(Object[] aentity)
    {
        if (aentity != null && aentity.length > 0)
        {
            for (int i = 0; i < aentity.length; i++)
            {
                update(aentity[i]);
            }
        }
    }

    /**
     * 批量更新数据
     */
    @Override
    public void update(Collection<?> entities)
    {
        if (entities != null && !entities.isEmpty())
        {
            for (Object entity : entities)
            {
                update(entity);
            }
        }
    }

    /**
     * 删除数据
     */
    @Override
    public void delete(Object entity)
    {
        getHibernateTemplate().delete(entity);
    }

    /**
     * 批量删除数据
     */
    @Override
    public void delete(Collection<?> entities)
    {
        getHibernateTemplate().deleteAll(entities);
    }

    /**
     * 根据id删除数据
     */
    @Override
    public void deleteById(Class<?> clazz, Serializable id)
    {
        getHibernateTemplate().delete(get(clazz, id));
    }

    /**
     * 根据id获取数据
     */
    @Override
    public <T> T get(Class<T> clazz, Serializable id)
    {
        return getHibernateTemplate().get(clazz, id);
    }

    /**
     * 获取所有数据
     */
    @Override
    public <T> List<T> getAll(Class<T> clazz)
    {
        return getHibernateTemplate().loadAll(clazz);
    }

    /**
     * hql查询数据量
     * @param statement hql
     */
    @Override
    public Long getCount(String statement, Map<String, Object> params)
    {
        statement = SQL_COUNT + removeSelect(removeOrders(statement));
        List<?> list = this.find(statement, params);
        return (Long) list.get(0);
    }

    protected String removeSelect(String statement)
    {
        statement = StringUtils.remove(statement, " fetch");
        int beginPos = statement.toLowerCase(Locale.US).indexOf("from");

        Assert.isTrue(beginPos != -1, " the statement : " + statement + " must has a keyword 'from'");
        return statement.substring(beginPos);
    }

    private static final Pattern REMOVE_ORDERS_PATTERN = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*",
            Pattern.CASE_INSENSITIVE);

    protected String removeOrders(String statement)
    {
        Matcher matcher = REMOVE_ORDERS_PATTERN.matcher(statement);

        StringBuffer buffer = new StringBuffer();
        while (matcher.find())
        {
            matcher.appendReplacement(buffer, "");
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }

    private static final Pattern REMOVE_GROUP_PATTERN = Pattern.compile("group\\s*by[\\w|\\W|\\s|\\S]*",
            Pattern.CASE_INSENSITIVE);

    protected String removeGroup(String statement)
    {
        Matcher matcher = REMOVE_GROUP_PATTERN.matcher(statement);

        StringBuffer buffer = new StringBuffer();
        while (matcher.find())
        {
            matcher.appendReplacement(buffer, "");
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }

    /**
     * 根据hql查询数据
     * @param statement hql
     */
    @Override
    public List<?> find(final String statement, final Map<String, Object> params)
    {

        return this.getHibernateTemplate().execute(new HibernateCallback<List<?>>()
        {

            @Override
            public List<?> doInHibernate(Session session) throws HibernateException
            {
                Query query = session.createQuery(statement);

                if (params != null)
                {
                    for (Map.Entry<String, Object> entry : params.entrySet())
                    {
                        String name = entry.getKey();
                        Object value = entry.getValue();

                        if (value instanceof Collection)
                        {
                            query.setParameterList(name, (Collection<?>) value);
                        } else if (value instanceof Object[])
                        {
                            query.setParameterList(name, (Object[]) value);
                        } else
                        {
                            query.setParameter(name, value);
                        }

                    }
                }

                return query.list();
            }
        });
    }

    /**
     * 根据sql查询数据
     * @param sql 
     */
    @Override
    public List<Object[]> findByNativeSQL(final String sql, final Object... values)
    {
        return this.findByNativeSQL(sql, null, values);
    }

    /**
     * 根据sql语句进行单值查询
     * @param sql 
     */
    @Override
    public Object findObjectByNativeSQL(final String sql, final Object... values)
    {
        return getHibernateTemplate().execute(new HibernateCallback<Object>()
        {

            @Override
            public Object doInHibernate(Session session) throws HibernateException
            {
                Query query = session.createSQLQuery(sql);

                if (values != null)
                {
                    for (int i = 0; i < values.length; i++)
                    {
                        query.setParameter(i, values[i]);
                    }
                }

                return query.uniqueResult();
            }

        });
    }

    /**
     * 根据hql语句进行单值查询
     * @param hql
     * @param values
     * @return
     */
    @Override
    public Object findUniqueResult(final String hql, final Object... values)
    {
        return getHibernateTemplate().execute(new HibernateCallback<Object>()
        {

            @Override
            public Object doInHibernate(Session session) throws HibernateException
            {
                Query query = session.createQuery(hql);

                if (values != null)
                {
                    for (int i = 0; i < values.length; i++)
                    {
                        query.setParameter(i, values[i]);
                    }
                }

                return query.uniqueResult();
            }

        });
    }

    /**
     * 根据 QueryRule 进行单值查询
     * @param <T>
     * @param queryRule
     * @param clazz
     * @return
     */
    @Override
    public <T> T findUniqueResult(final QueryRule queryRule, final Class<T> clazz)
    {
        return (T) getHibernateTemplate().execute(new HibernateCallback<T>()
        {

            @Override
            public T doInHibernate(Session session) throws HibernateException
            {
                Criteria criteria = session.createCriteria(clazz);
                QueryRuleSupport.createCriteriaWithQueryRule(criteria, queryRule);

                CriteriaImpl impl = (CriteriaImpl) criteria;
                Projection projection = impl.getProjection();

                criteria.setProjection(projection);
                if (projection == null)
                {
                    criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
                }

                return (T) criteria.uniqueResult();
            }
        });
    }

    /**
     * 立即保存数据
     */
    @Override
    public void flush()
    {
        getHibernateTemplate().flush();
    }

    /**
     * 清除缓存
     */
    @Override
    public void clear()
    {
        getHibernateTemplate().clear();
    }

    /**
     * 对象托管
     */
    @Override
    public void evict(Object entity)
    {
        getHibernateTemplate().evict(entity);
    }

    /**
     * Check whether the given object is in the Session cache.
     */
    @Override
    public boolean contains(Object entity)
    {
        return getHibernateTemplate().contains(entity);
    }

    /**
     * 根据hql查询分页数据
     * @param statement hql
     */
    @Override
    public Page find(final String statement, final Map<String, Object> params, final int pageIndex, final int pageSize)
    {

        return (Page) getHibernateTemplate().execute(new HibernateCallback<Page>()
        {
            @Override
            public Page doInHibernate(Session session) throws HibernateException
            {
                Page page = new Page(pageSize, pageIndex);

                Long totalCount = getCount(statement, params);
                if (totalCount < 1)
                {
                    return page;
                }

                Query query = session.createQuery(statement);
                if (params != null)
                {
                    for (Map.Entry<String, Object> entry : params.entrySet())
                    {
                        String name = entry.getKey();
                        Object value = entry.getValue();

                        if (value instanceof Collection)
                        {
                            query.setParameterList(name, (Collection) value);
                        } else if (value instanceof Object[])
                        {
                            query.setParameterList(name, (Object[]) value);
                        } else
                        {
                            query.setParameter(name, value);
                        }
                    }
                }

                List result = query.setFirstResult(page.getRecordIndex()).setMaxResults(pageSize).list();
                page.setTotalCount(totalCount.intValue());
                page.setResult(result);

                return page;
            }
        });
    }

    /**
     * sql 语句 where
     */
    private static final String SQL_WHERE = "where";

    /**
     * SQL 计数
     */
    private static final String SQL_COUNT = "select count (1) ";

    /**
     * 根据sql查询数据量
     * @param sql
     */
    @Override
    public Long getCountByNativeSQL(String sql, Object... values)
    {
        int index = StringUtils.lastIndexOfIgnoreCase(sql, SQL_WHERE);

        sql = SQL_COUNT + removeSelect(StringUtils.substring(sql, 0, index)) + SQL_WHERE
                + removeGroup(removeOrders(StringUtils.substring(sql, index + 5, sql.length())));
        BigDecimal count = (BigDecimal) this.findObjectByNativeSQL(sql, values);
        return count.longValue();
    }

    /**
     * 根据sql查询分页数据
     * @param sql
     */
    @Override
    public Page findByNativeSQL(final String sql, final Class<?> clazz, final int pageIndex, final int pageSize,
            final Object... values)
    {
        return (Page) getHibernateTemplate().execute(new HibernateCallback<Page>()
        {
            @Override
            public Page doInHibernate(Session session) throws HibernateException
            {
                Page page = new Page(pageSize, pageIndex);

                Long totalCount = getCountByNativeSQL(sql, values);
                if (totalCount < 1)
                {
                    return page;
                }
                SQLQuery query = session.createSQLQuery(sql);

                if (clazz != null)
                {
                    query.addEntity(clazz);
                }

                if (values != null)
                {
                    for (int i = 0; i < values.length; i++)
                    {
                        query.setParameter(i, values[i]);
                    }
                }

                List result = query.setFirstResult(page.getRecordIndex()).setMaxResults(pageSize).list();
                page.setTotalCount(totalCount.intValue());
                page.setResult(result);

                return page;
            }

        });
    }

    /**
     * 根据sql查询分页数据
     * @param sql
     */
    @Override
    public Page findByNativeSQL(String sql, int pageIndex, int pageSize, Object... values)
    {
        return this.findByNativeSQL(sql, null, pageIndex, pageSize, values);
    }

    /**
     * 根据sql查询分页数据,结果为MAP
     * @param sql
     */
    @Override
    public Page findMapByNativeSQL(final String sql, final int pageIndex, final int pageSize, final Object... values)
    {
        return getHibernateTemplate().execute(new HibernateCallback<Page>()
        {
            @Override
            public Page doInHibernate(Session session) throws HibernateException
            {
                Page page = new Page(pageSize, pageIndex);

                Long totalCount = getCountByNativeSQL(sql, values);
                if (totalCount < 1)
                {
                    return page;
                }
                SQLQuery query = session.createSQLQuery(sql);
                query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

                if (values != null)
                {
                    for (int i = 0; i < values.length; i++)
                    {
                        query.setParameter(i, values[i]);
                    }
                }

                List result = query.setFirstResult(page.getRecordIndex()).setMaxResults(pageSize).list();
                page.setTotalCount(totalCount.intValue());
                page.setResult(result);

                return page;
            }

        });
    }

    /**
     * 根据QueryRule查询分页数据
     * @param queryRule
     */
    @Override
    public Page find(final QueryRule queryRule, final Class<?> clazz, final int pageIndex, final int pageSize)
    {
        return (Page) getHibernateTemplate().execute(new HibernateCallback<Page>()
        {
            @Override
            public Page doInHibernate(Session session) throws HibernateException
            {
                Page page = new Page(pageSize, pageIndex);

                Criteria criteria = session.createCriteria(clazz);
                QueryRuleSupport.createCriteriaWithQueryRule(criteria, queryRule);

                CriteriaImpl impl = (CriteriaImpl) criteria;
                Projection projection = impl.getProjection();

                List<OrderEntry> orderEntries = getOrders(impl);

                BeanUtil.setProperty(impl, "orderEntries", new ArrayList());

                int totalCount = ((Long) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
                if (totalCount < 1)
                {
                    return page;
                }

                criteria.setProjection(projection);
                if (projection == null)
                {
                    criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
                }
                BeanUtil.setProperty(impl, "orderEntries", orderEntries);

                List result = criteria.setFirstResult(page.getRecordIndex()).setMaxResults(pageSize).list();
                page.setTotalCount(totalCount);
                page.setResult(result);

                return page;
            }

        });
    }

    /**
     * 处理对象属性
     * @Package com.hdrs.aml.dao.crud.dao.impl
     * @ClassName: BeanUtil
     * @author 邵欣
     * @date 2017年9月6日 下午8:26:52
     */
    private static class BeanUtil
    {
        /**
         * 根据属性名和属性值来设置对象的属性
         * 
         * @param object
         *            需要放置属性值的对象
         * @param propertyName
         *            属性名
         * @param newValue
         *            属性值
         */
        public static void setProperty(Object object, String propertyName, Object newValue)
        {
            Assert.notNull(object, "对象不能为空");
            Assert.hasText(propertyName, "属性不能为空");

            try
            {
                Field field = getDeclaredField(object.getClass(), propertyName);
                boolean accessible = field.isAccessible();
                field.setAccessible(true);
                field.set(object, newValue);
                field.setAccessible(accessible);
            } catch (NoSuchFieldException nsfe)
            {
                throw new RuntimeException(nsfe.getMessage());
            } catch (Exception e)
            {
                throw new InternalError("Runtime Exception impossibility throw");
            }

        }

        /**
         * 根据属性名，在当前Class类实例的所有Field对象中（包括父类的Field）检索对应的属性值
         * 
         * @param clazz
         *            Class类实例
         * @param propertyName
         *            属性名
         * @return Field 对象
         * 
         * @throws NoSuchFieldException
         */
        public static Field getDeclaredField(Class<?> clazz, String propertyName) throws NoSuchFieldException
        {
            Assert.notNull(clazz, "对象不能为空");
            Assert.hasText(propertyName, "属性不能为空");

            for (Class<?> superClass = clazz; superClass != Object.class;)
            {
                try
                {
                    return superClass.getDeclaredField(propertyName);
                } catch (NoSuchFieldException e)
                {
                    superClass = superClass.getSuperclass();
                }
            }

            throw new NoSuchFieldException("No such field: " + clazz.getName() + '.' + propertyName);

        }
    }

    protected List<OrderEntry> getOrders(Criteria criteria)
    {
        CriteriaImpl impl = (CriteriaImpl) criteria;
        try
        {
            Field field = criteria.getClass().getDeclaredField("orderEntries");
            field.setAccessible(true);
            return (List<OrderEntry>) field.get(impl);
        } catch (Exception e)
        {
            throw new InternalError(" Runtime Exception impossibility can't throw ");
        }
    }

    /**
     * 更据hql更新数据
     * @param hql
     */
    @Override
    public int executeUpdate(String hql, Object... values)
    {
        return getHibernateTemplate().bulkUpdate(hql, values);
    }

    /**
     * 更据sql更新数据
     * @param sql
     */
    @Override
    public int executeUpdateByNativeSQL(final String sql, final Object... values)
    {
        int updateCount = getHibernateTemplate().execute(new HibernateCallback<Integer>()
        {
            @Override
            public Integer doInHibernate(Session session) throws HibernateException
            {
                SQLQuery query = session.createSQLQuery(sql);

                if (values != null)
                {
                    for (int i = 0; i < values.length; i++)
                    {
                        query.setParameter(i, values[i]);
                    }
                }

                return query.executeUpdate();
            }
        });

        return updateCount;
    }

    /**
     * 更据sql查询列表
     * @param sql
     */
    @Override
    public <T> List<T> findByNativeSQL(final String sql, final Class<T> clazz, final Object... values)
    {
        return (List<T>) getHibernateTemplate().execute(new HibernateCallback<List<T>>()
        {
            @Override
            public List<T> doInHibernate(Session session) throws HibernateException
            {
                SQLQuery query = session.createSQLQuery(sql);
                if (clazz != null)
                {
                    query.addEntity(clazz);
                }

                if (values != null)
                {
                    for (int i = 0; i < values.length; i++)
                    {
                        query.setParameter(i, values[i]);
                    }
                }
                return query.list();
            }
        });
    }

    /**
     * 更据sql查询列表
     * @param sql
     */
    @Override
    public List<Map<String, Object>> findMapByNativeSQL(final String sql, final Object... values)
    {
        return getHibernateTemplate().execute(new HibernateCallback<List<Map<String, Object>>>()
        {
            @Override
            public List<Map<String, Object>> doInHibernate(Session session) throws HibernateException
            {
                SQLQuery query = session.createSQLQuery(sql);
                query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

                if (values != null)
                {
                    for (int i = 0; i < values.length; i++)
                    {
                        query.setParameter(i, values[i]);
                    }
                }
                return query.list();
            }
        });
    }

    /**
     * 更据QueryRule查询列表
     * @param queryRule
     */
    @Override
    public <T> List<T> find(final QueryRule queryRule, final Class<T> clazz)
    {
        return getHibernateTemplate().execute(new HibernateCallback<List<T>>()
        {
            @Override
            public List<T> doInHibernate(Session session) throws HibernateException
            {
                Criteria criteria = session.createCriteria(clazz);
                QueryRuleSupport.createCriteriaWithQueryRule(criteria, queryRule);

                CriteriaImpl impl = (CriteriaImpl) criteria;
                Projection projection = impl.getProjection();

                criteria.setProjection(projection);
                if (projection == null)
                {
                    criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
                }

                return criteria.list();
            }
        });
    }

    /**
     * 
     * 特殊的自定义sql查询
     */
    @Override
    public Page findMapByNativeSpecialSQL(final String sql, final int pageIndex, final int pageSize,
            final Object... values)
    {
        return getHibernateTemplate().execute(new HibernateCallback<Page>()
        {
            @Override
            public Page doInHibernate(Session session) throws HibernateException
            {
                Page page = new Page(pageSize, pageIndex);
                String sqlcount = " select count (*) " + "from (" + sql + ")";
                BigDecimal count = (BigDecimal) findObjectByNativeSQL(sqlcount, values);
                Long totalCount = count.longValue();
                if (totalCount < 1)
                {
                    return page;
                }
                SQLQuery query = session.createSQLQuery(sql);
                query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

                if (values != null)
                {
                    for (int i = 0; i < values.length; i++)
                    {
                        query.setParameter(i, values[i]);
                    }
                }

                List result = query.setFirstResult(page.getRecordIndex()).setMaxResults(pageSize).list();
                page.setTotalCount(totalCount.intValue());
                page.setResult(result);

                return page;
            }

        });
    }

    /**
     * 执行无返回结果的存储过程
     * @param pd
     * @param values
     * @see com.hdrs.aml.dao.crud.dao.Dao#executeVoidProcedure(java.lang.String, java.lang.Object[])
     */
    @Override
    public void executeVoidProcedure(final String pd, final Object... values)
    {
        getHibernateTemplate().execute(new HibernateCallback<Object>()
        {
            @Override
            public Object doInHibernate(Session session) throws HibernateException
            {
                session.doWork(new Work()
                {
                    @Override
                    public void execute(Connection conn) throws SQLException
                    {
                        CallableStatement call = conn.prepareCall("{ call " + pd + " }");
                        if (null != values)
                        {
                            for (int i = 0; i < values.length; i++)
                            {
                                call.setObject(i + 1, values[i]);
                            }
                        }
                        call.executeQuery();
                    }
                });

                return null;
            }
        });
    }

    /**
     * 通过存储过程查询(单结果集)
     * @param pd
     * @param values
     * @return List<Map<String,Object>>
     */
    @Override
    public List<Map<String, Object>> executeSingletonProcedure(final String pd, final Object... values)
    {
        final List<Map<String, Object>> result = Lists.newArrayList();

        return getHibernateTemplate().execute(new HibernateCallback<List<Map<String, Object>>>()
        {
            @Override
            public List<Map<String, Object>> doInHibernate(Session session) throws HibernateException
            {
                session.doWork(new Work()
                {
                    @Override
                    public void execute(Connection conn) throws SQLException
                    {
                        CallableStatement call = conn.prepareCall("{ call " + pd + " }");
                        if (null != values)
                        {
                            for (int i = 0; i < values.length; i++)
                            {
                                call.setObject(i + 1, values[i]);
                            }
                        }
                        ResultSet rst = call.executeQuery();
                        ResultSetMetaData metaData = rst.getMetaData();
                        int colCount = metaData.getColumnCount();
                        while (rst.next())
                        {
                            Map<String, Object> map = Maps.newHashMap();
                            for (int i = 1; i <= colCount; i++)
                            {
                                String colName = metaData.getColumnName(i);
                                map.put(colName, rst.getObject(colName));
                            }
                            result.add(map);
                        }
                    }
                });
                return result;
            }
        });
    }

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
    @Override
    public Map<String, Object> executeOutParamProcedure(final String pd, final String outParam,
            final Integer outParamType, final Object... ins)
    {
        final Map<String, Object> rst = Maps.newHashMap();
        return getHibernateTemplate().execute(new HibernateCallback<Map<String, Object>>()
        {
            @Override
            public Map<String, Object> doInHibernate(Session session) throws HibernateException
            {
                session.doWork(new Work()
                {
                    @Override
                    public void execute(Connection conn) throws SQLException
                    {
                        CallableStatement call = conn.prepareCall("{ call " + pd + " }");
                        if (null != ins)
                        {
                            for (int i = 0; i < ins.length; i++)
                            {
                                call.setObject(i + 1, ins[i]);
                            }
                        }
                        call.registerOutParameter(ins.length + 1, outParamType);
                        call.execute();

                        rst.put(outParam, call.getObject(ins.length + 1));
                    }
                });
                return rst;
            }
        });
    }

}
