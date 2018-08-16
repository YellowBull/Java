package com.jmev.cn.dao.crud.dao.support;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.jmev.cn.dao.crud.query.QueryRule;
import com.jmev.cn.dao.crud.query.QueryRule.Rule;

/**
 * 查询规则支持类
 * @author 邵欣
 */
public class QueryRuleSupport {
	
	/**
	 * 根据传入的Criteria，以及查询规则QueryRule，生成查询对象。
	 * 
	 * @param criteria
	 * @param queryRule
	 */
	public static void createCriteriaWithQueryRule(Criteria criteria, QueryRule queryRule) {
		for (Iterator<Rule> iterator = queryRule.getRules().iterator(); iterator.hasNext();) {
			Rule rule = iterator.next();

			switch (rule.getType()) {
				case QueryRule.LIKE:
					processLike(criteria, rule);
					break;
				case QueryRule.IN:
					processIN(criteria, rule);
					break;
				case QueryRule.BETWEEN:
					processBetween(criteria, rule);
					break;
				case QueryRule.EQ:
					processEqual(criteria, rule);
					break;
				case QueryRule.NOTEQ:
					processNotEqual(criteria, rule);
					break;
				case QueryRule.GT:
					processGreaterThen(criteria, rule);
					break;
				case QueryRule.GE:
					processGreaterEqual(criteria, rule);
					break;
				case QueryRule.LT:
					processLessThen(criteria, rule);
					break;
				case QueryRule.LE:
					processLessEqual(criteria, rule);
					break;
				case QueryRule.SQL:
					processSQL(criteria, rule);
					break;
				case QueryRule.ISNULL:
					processIsNull(criteria, rule);
					break;
				case QueryRule.ISNOTNULL:
					processIsNotNull(criteria, rule);
					break;
				case QueryRule.ISEMPTY:
					processIsEmpty(criteria, rule);
					break;
				case QueryRule.ISNOTEMPTY:
					processIsNotEmpty(criteria, rule);
					break;
				case QueryRule.ASC_ORDER:
					processAscOrder(criteria, rule);
					break;
				case QueryRule.DESC_ORDER:
					processDescOrder(criteria, rule);
					break;
				case QueryRule.OR:
					processOr(criteria, rule);
					break;
				case QueryRule.NOTIN:
					processNotIN(criteria, rule);
					break;
				default:
					throw new IllegalArgumentException("type " + rule.getType() + " not supported.");
			}

		}

		for (Iterator<QueryRule> iterator = queryRule.getQueryRules().iterator(); iterator.hasNext();) {
			QueryRule subQueryRule = iterator.next();

			Criteria subCriteria = criteria.createCriteria(subQueryRule.getPropertyName());
			createCriteriaWithQueryRule(subCriteria, subQueryRule);
		}

	}
	
	private static void createDisjunction(Disjunction dis, QueryRule q) {
		List<Rule> rules = q.getRules();
		for (Rule rule : rules) {
			switch (rule.getType()) {
				case QueryRule.LIKE:
					processLike(dis, rule);
					break;
				case QueryRule.IN:
					processIN(dis, rule);
					break;
				case QueryRule.BETWEEN:
					processBetween(dis, rule);
					break;
				case QueryRule.EQ:
					processEqual(dis, rule);
					break;
				case QueryRule.NOTEQ:
					processNotEqual(dis, rule);
					break;
				case QueryRule.GT:
					processGreaterThen(dis, rule);
					break;
				case QueryRule.GE:
					processGreaterEqual(dis, rule);
					break;
				case QueryRule.LT:
					processLessThen(dis, rule);
					break;
				case QueryRule.LE:
					processLessEqual(dis, rule);
					break;
				case QueryRule.SQL:
					processSQL(dis, rule);
					break;
				case QueryRule.ISNULL:
					processIsNull(dis, rule);
					break;
				case QueryRule.ISNOTNULL:
					processIsNotNull(dis, rule);
					break;
				case QueryRule.ISEMPTY:
					processIsEmpty(dis, rule);
					break;
				case QueryRule.ISNOTEMPTY:
					processIsNotEmpty(dis, rule);
					break;
				case QueryRule.NOTIN:
					processNotIN(dis, rule);
					break;
				default:
					throw new IllegalArgumentException("type " + rule.getType() + " not supported.");
			}
		}
	}

	/**
	 * 对应SQL的like子句
	 * 
	 * @param criteria
	 * @param rule
	 */
	protected static void processLike(Criteria criteria, Rule rule) {
		Object[] values = rule.getValues();
		if (ArrayUtils.isEmpty(values)){
			return;
		}
		Object obj = values[0];
		if (obj != null) {
			String value = obj.toString();
			if (StringUtils.isNotEmpty(value)) {
				// value = value.replace('*', '%');
				value = "%" + value + "%";

				obj = value;
			}
		}

		criteria.add(Restrictions.like(rule.getPropertyName(), obj));

	}
	
	protected static void processLike(Disjunction criteria, Rule rule) {
		Object[] values = rule.getValues();
		if (ArrayUtils.isEmpty(values)) {
			return;
		}

		Object obj = values[0];
		if (obj != null) {
			String value = obj.toString();
			if (StringUtils.isNotEmpty(value)) {
				// value = value.replace('*', '%');
				value = "%" + value + "%";

				obj = value;
			}
		}

		criteria.add(Restrictions.like(rule.getPropertyName(), obj));

	}

	/**
	 * 对应SQL的between子句
	 * 
	 * @param criteria
	 * @param rule
	 */
	protected static void processBetween(Criteria criteria, Rule rule) {
		Object[] values = rule.getValues();
		if (ArrayUtils.isEmpty(values) || values.length < 2) {
			return;
		}

		criteria.add(Restrictions.between(rule.getPropertyName(), values[0], values[1]));

	}
	
	protected static void processBetween(Disjunction criteria, Rule rule) {
		Object[] values = rule.getValues();
		if (ArrayUtils.isEmpty(values) || values.length < 2) {
			return;
		}
		criteria.add(Restrictions.between(rule.getPropertyName(), values[0], values[1]));

	}

	/**
	 * =等于
	 * 
	 * @param criteria
	 * @param rule
	 */
	protected static void processEqual(Criteria criteria, Rule rule) {
		Object[] values = rule.getValues();
		if (ArrayUtils.isEmpty(values)) {
			return;
		}

		criteria.add(Restrictions.eq(rule.getPropertyName(), values[0]));

	}
	
	protected static void processEqual(Disjunction criteria, Rule rule) {
		Object[] values = rule.getValues();
		if (ArrayUtils.isEmpty(values)) {
			return;
		}
		criteria.add(Restrictions.eq(rule.getPropertyName(), values[0]));

	}

	/**
	 * !=不等于
	 * 
	 * @param criteria
	 * @param rule
	 */
	protected static void processNotEqual(Criteria criteria, Rule rule) {
		Object[] values = rule.getValues();
		if (ArrayUtils.isEmpty(values)) {
			return;
		}
		criteria.add(Restrictions.ne(rule.getPropertyName(), values[0]));

	}
	
	protected static void processNotEqual(Disjunction criteria, Rule rule) {
		Object[] values = rule.getValues();
		if (ArrayUtils.isEmpty(values)) {
			return;
		}
		criteria.add(Restrictions.ne(rule.getPropertyName(), values[0]));

	}

	/**
	 * >大于
	 * 
	 * @param criteria
	 * @param rule
	 */
	protected static void processGreaterThen(Criteria criteria, Rule rule) {
		Object[] values = rule.getValues();
		if (ArrayUtils.isEmpty(values)){
			return;
		}

		criteria.add(Restrictions.gt(rule.getPropertyName(), values[0]));

	}
	
	protected static void processGreaterThen(Disjunction criteria, Rule rule) {
		Object[] values = rule.getValues();
		if (ArrayUtils.isEmpty(values)) {
			return;
		}
		criteria.add(Restrictions.gt(rule.getPropertyName(), values[0]));

	}
	
	/**
	 * >=大于等于
	 * 
	 * @param criteria
	 * @param rule
	 */
	protected static void processGreaterEqual(Criteria criteria, Rule rule) {
		Object[] values = rule.getValues();
		if (ArrayUtils.isEmpty(values)) {
			return;
		}
		criteria.add(Restrictions.ge(rule.getPropertyName(), values[0]));

	}
	
	protected static void processGreaterEqual(Disjunction criteria, Rule rule) {
		Object[] values = rule.getValues();
		if (ArrayUtils.isEmpty(values)) {
			return;
		}
		criteria.add(Restrictions.ge(rule.getPropertyName(), values[0]));

	}

	/**
	 * <小于
	 * 
	 * @param criteria
	 * @param rule
	 */
	protected static void processLessThen(Criteria criteria, Rule rule) {
		Object[] values = rule.getValues();
		if (ArrayUtils.isEmpty(values)) {
			return;
		}

		criteria.add(Restrictions.lt(rule.getPropertyName(), values[0]));

	}
	
	protected static void processLessThen(Disjunction criteria, Rule rule) {
		Object[] values = rule.getValues();
		if (ArrayUtils.isEmpty(values)) {
			return;
		}
		criteria.add(Restrictions.lt(rule.getPropertyName(), values[0]));

	}

	/**
	 * <=小于等于
	 * 
	 * @param criteria
	 * @param rule
	 */
	protected static void processLessEqual(Criteria criteria, Rule rule) {
		Object[] values = rule.getValues();
		if (ArrayUtils.isEmpty(values)) {
			return;
		}
		criteria.add(Restrictions.le(rule.getPropertyName(), rule.getValues()[0]));

	}
	
	protected static void processLessEqual(Disjunction criteria, Rule rule) {
		Object[] values = rule.getValues();
		if (ArrayUtils.isEmpty(values)) {
			return;
		}
		criteria.add(Restrictions.le(rule.getPropertyName(), rule.getValues()[0]));

	}

	/**
	 * SQL限定查询
	 * 
	 * @param criteria
	 * @param rule
	 */
	protected static void processSQL(Criteria criteria, Rule rule) {
		criteria.add(Restrictions.sqlRestriction(rule.getPropertyName()));
	}
	
	protected static void processSQL(Disjunction criteria, Rule rule) {
		criteria.add(Restrictions.sqlRestriction(rule.getPropertyName()));
	}

	/**
	 * 判断属性是否为空，为空则返回true，反之返回false
	 * 
	 * @param criteria
	 * @param rule
	 */
	protected static void processIsNull(Criteria criteria, Rule rule) {
		criteria.add(Restrictions.isNull(rule.getPropertyName()));
	}
	
	protected static void processIsNull(Disjunction criteria, Rule rule) {
		criteria.add(Restrictions.isNull(rule.getPropertyName()));
	}

	/**
	 * 判断属性是否为空，不为空则返回true，反之返回false
	 * 
	 * @param criteria
	 * @param rule
	 */
	protected static void processIsNotNull(Criteria criteria, Rule rule) {
		criteria.add(Restrictions.isNotNull(rule.getPropertyName()));
	}
	
	protected static void processIsNotNull(Disjunction criteria, Rule rule) {
		criteria.add(Restrictions.isNotNull(rule.getPropertyName()));
	}

	/**
	 * 判断集合属性是否不为空，不为空则返回true，反之返回false
	 * 
	 * @param criteria
	 * @param rule
	 */
	protected static void processIsNotEmpty(Criteria criteria, Rule rule) {
		criteria.add(Restrictions.isNotEmpty(rule.getPropertyName()));
	}
	
	protected static void processIsNotEmpty(Disjunction criteria, Rule rule) {
		criteria.add(Restrictions.isNotEmpty(rule.getPropertyName()));
	}

	/**
	 * 判断集合属性是否为空，为空则返回true，反之返回false
	 * 
	 * @param criteria
	 * @param rule
	 */
	protected static void processIsEmpty(Criteria criteria, Rule rule) {
		criteria.add(Restrictions.isEmpty(rule.getPropertyName()));
	}
	
	protected static void processIsEmpty(Disjunction criteria, Rule rule) {
		criteria.add(Restrictions.isEmpty(rule.getPropertyName()));
	}

	/**
	 * 对应SQL的in子句
	 * 
	 * @param criteria
	 * @param rule
	 */
	protected static void processIN(Criteria criteria, Rule rule) {
		Object[] values = rule.getValues();
		if (ArrayUtils.isEmpty(values)) {
			return;
		}
		if (values.length == 1 && values[0] != null && (values[0] instanceof List)) {
			List<?> list = (List<?>) values[0];
			if (list != null && !list.isEmpty()) {
				criteria.add(Restrictions.in(rule.getPropertyName(), list));
			}
		} else {
			criteria.add(Restrictions.in(rule.getPropertyName(), values));
		}
	}
	
	protected static void processIN(Disjunction criteria, Rule rule) {
		Object[] values = rule.getValues();
		if (ArrayUtils.isEmpty(values)) {
			return;
		}
		if (values.length == 1 && values[0] != null && (values[0] instanceof List)) {
			List<?> list = (List<?>) values[0];
			if (list != null && !list.isEmpty()) {
				criteria.add(Restrictions.in(rule.getPropertyName(), list));
			}
		} else {
			criteria.add(Restrictions.in(rule.getPropertyName(), values));
		}
	}

	/**
	 * 根据属性升序排列
	 * 
	 * @param criteria
	 * @param rule
	 */
	protected static void processAscOrder(Criteria criteria, Rule rule) {
		criteria.addOrder(Order.asc(rule.getPropertyName()));
	}

	/**
	 * 根据属性降序排列
	 * 
	 * @param criteria
	 * @param rule
	 */
	protected static void processDescOrder(Criteria criteria, Rule rule) {
		criteria.addOrder(Order.desc(rule.getPropertyName()));
	}
	
	/**
	 * or 查询
	 * @param criteria
	 * @param rule
	 */
	protected static void processOr(Criteria criteria, Rule rule) {
		QueryRule queryRule = rule.getQueryRule();
		if (queryRule == null || queryRule.getRules() == null || queryRule.getRules().isEmpty()) {
			return;
		}
		Disjunction dis = Restrictions.disjunction();
		
		createDisjunction(dis, queryRule);
		
		criteria.add(dis);
	}
	
	/**
	 * 对应SQL的notin子句
	 * 
	 * @param criteria
	 * @param rule
	 */
	protected static void processNotIN(Criteria criteria, Rule rule) {
		Object[] values = rule.getValues();
		if (ArrayUtils.isEmpty(values)) {
			return;
		}
		if (values.length == 1 && values[0] != null && (values[0] instanceof List)) {
			List<?> list = (List<?>) values[0];
			if (list != null && !list.isEmpty()) {
				criteria.add(Restrictions.not(Restrictions.in(rule.getPropertyName(), list)));
			}
		} else {
			criteria.add(Restrictions.not(Restrictions.in(rule.getPropertyName(), values)));
		}
	}
	
	protected static void processNotIN(Disjunction criteria, Rule rule) {
		Object[] values = rule.getValues();
		if (ArrayUtils.isEmpty(values)) {
			return;
		}

		if (values.length == 1 && values[0] != null && (values[0] instanceof List)) {
			List<?> list = (List<?>) values[0];
			if (list != null && !list.isEmpty()) {
				criteria.add(Restrictions.not(Restrictions.in(rule.getPropertyName(), list)));
			}
		} else {
			criteria.add(Restrictions.not(Restrictions.in(rule.getPropertyName(), values)));
		}
	}
}
