package com.jmev.cn.dao.crud.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.jmev.cn.constant.CarinfoConstant;

/**
 * @author 邵欣
 */
public class QueryRule implements Serializable {
	private static final long serialVersionUID = 7161471344020755701L;
	
	/** 根据属性升序排列 */
	public static final int ASC_ORDER = 101;

	/** 根据属性降序排列句 */
	public static final int DESC_ORDER = 102;

	/** 对应SQL的like子句 */
	public static final int LIKE = 1;

	/** 对应SQL的in子句 */
	public static final int IN = 2;

	/** 对应SQL的between子句 */
	public static final int BETWEEN = 3;

	/** =等于 */
	public static final int EQ = 4;

	/** !=不等于 */
	public static final int NOTEQ = 5;

	/** >大于 */
	public static final int GT = 6;

	/** >=大于等于 */
	public static final int GE = 7;

	/** <小于 */
	public static final int LT = 8;

	/** <=小于等于 */
	public static final int LE = 9;

	/** SQL限定查询 */
	public static final int SQL = 10;

	/** 判断属性是否为空，为空则返回true，反之返回false */
	public static final int ISNULL = 11;

	/** 判断属性是否为空，不为空则返回true，反之返回false */
	public static final int ISNOTNULL = 12;

	/** 判断集合属性是否不为空，不为空则返回true，反之返回false */
	public static final int ISEMPTY = 13;

	/** 判断集合属性是否为空，为空则返回true，反之返回false */
	public static final int ISNOTEMPTY = 14;
	
	/** 对应SQL的 or */
	public static final int OR = 15;
	
	/** 对应SQL的not in  */
	public static final int NOTIN = 16;
	
	/**
	 * 传 baseEntity 中 isDeleted 属性值 Boolean.FALSE/Boolean.TRUE
	 * 默认无参数为查询未删除的
	 * 传Boolean.TRUE查询已删除的
	 * 传Boolean.FALSE查询未删除的
	 * 传BusiConstant.HAD_DEL 查询所有包括 删除和未删除的
	 * @param isDeleted default: Boolean.FALSE
	 * @return QueryRule
	 */
	public static QueryRule getInstance(Object... isDeleted) {
		QueryRule instance = new QueryRule();
		
		if (isDeleted != null && isDeleted.length > 0) {
			
			if (CarinfoConstant.HAD_DEL.equals(isDeleted[0])) {
				//查询所有不附加条件
			} else {//按传入条件查询-一般为已删除的
				instance.addEqual("isDeleted", isDeleted[0]);
			}
		} else {//默认查询未删除的
			instance.addEqual("isDeleted", Boolean.FALSE);
		}
		
		return instance;
	}

	/** 查询对应的对象名 */
	private String propertyName;

	/** 规则 */
	private List<Rule> rules = new ArrayList<Rule>();

	/** 子查询规则 */
	private List<QueryRule> queryRules = new ArrayList<QueryRule>();

	/**
	 * 
	 */
	public QueryRule() {
	}

	/**
	 * @param propertyName
	 */
	public QueryRule(String propertyName) {
		this.propertyName = propertyName;
	}

	public class Rule implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 7515520660989476158L;

		/** 规则的类型 */
		private int type;

		/** 规则对应的属性名 */
		private String propertyName;

		/** 规则对应的属性值 */
		private Object[] values;
		
		/** or 查询*/
		private QueryRule queryRule;

		/**
		 * @param type
		 * @param propertyName
		 * @param values
		 */
		public Rule(int type, String propertyName, Object[] values) {
			this.type = type;
			this.propertyName = propertyName;
			this.values = values;
		}

		/**
		 * @param type
		 * @param propertyName
		 */
		public Rule(int type, String propertyName) {
			this.type = type;
			this.propertyName = propertyName;
		}
		
		/**
		 * 
		 * @param type
		 * @param propertyNameValue
		 */
		public Rule(int type, QueryRule queryRule){
			this.type = type;
			this.queryRule = queryRule;
		}

		/**
		 * @return the type
		 */
		public int getType() {
			return type;
		}

		/**
		 * @return the propertyName
		 */
		public String getPropertyName() {
			return propertyName;
		}

		/**
		 * @return the values
		 */
		public Object[] getValues() {
			return values;
		}

		/**
		 * 
		 * @return queryRule
		 */
		public QueryRule getQueryRule() {
			return queryRule;
		}

	}

	public QueryRule addAscOrder(String propertyName) {
		if (StringUtils.isNotBlank(propertyName)) {
			rules.add(new Rule(ASC_ORDER, propertyName));
		}
		return this;
	}

	public QueryRule addDescOrder(String propertyName) {
		if (StringUtils.isNotBlank(propertyName)) {
			rules.add(new Rule(DESC_ORDER, propertyName));
		}
		return this;
	}

	public QueryRule addLike(String propertyName, Object value) {
		if (value != null && StringUtils.isNotEmpty(value.toString())) {
			rules.add(new Rule(LIKE, propertyName, new Object[] { value }));
		}
		return this;
	}

	public QueryRule addBetween(String propertyName, Object values[]) {
		if (values == null || values.length != 2) {
			return this;
		}
		if (values[0] == null || StringUtils.isEmpty(values[0].toString())) {
			return this;
		}
		if (values[1] == null || StringUtils.isEmpty(values[1].toString())) {
			return this;
		}
		rules.add(new Rule(BETWEEN, propertyName, values));
		return this;
	}

	public QueryRule addIn(String propertyName, List<?> values) {
		if (values != null && !values.isEmpty()) {
			rules.add(new Rule(IN, propertyName, new Object[] { values }));
		}
		return this;
	}

	public QueryRule addIn(String propertyName, Object values[]) {
		if (values != null && values.length != 0) {
			rules.add(new Rule(IN, propertyName, values));
		}
		return this;
	}

	public QueryRule addEqual(String propertyName, Object value) {
		if (value != null && StringUtils.isNotEmpty(value.toString())) {
			rules.add(new Rule(EQ, propertyName, new Object[] { value }));
		}
		return this;
	}

	public QueryRule addNotEqual(String propertyName, Object value) {
		if (value != null && StringUtils.isNotEmpty(value.toString())) {
			rules.add(new Rule(NOTEQ, propertyName, new Object[] { value }));
		}
		return this;
	}

	public QueryRule addGreaterThan(String propertyName, Object value) {
		if (value != null && StringUtils.isNotEmpty(value.toString())) {
			rules.add(new Rule(GT, propertyName, new Object[] { value }));
		}
		return this;
	}

	public QueryRule addGreaterEqual(String propertyName, Object value) {
		if (value != null && StringUtils.isNotEmpty(value.toString())) {
			rules.add(new Rule(GE, propertyName, new Object[] { value }));
		}
		return this;
	}

	public QueryRule addLessThan(String propertyName, Object value) {
		if (value != null && StringUtils.isNotEmpty(value.toString())) {
			rules.add(new Rule(LT, propertyName, new Object[] { value }));
		}
		return this;
	}

	public QueryRule addLessEqual(String propertyName, Object value) {
		if (value != null && StringUtils.isNotEmpty(value.toString())) {
			rules.add(new Rule(LE, propertyName, new Object[] { value }));
		}
		return this;
	}

	public QueryRule addIsNull(String propertyName) {
		rules.add(new Rule(ISNULL, propertyName));
		return this;
	}

	public QueryRule addIsNotNull(String propertyName) {
		rules.add(new Rule(ISNOTNULL, propertyName));
		return this;
	}

	public QueryRule addIsEmpty(String propertyName) {
		rules.add(new Rule(ISEMPTY, propertyName));
		return this;
	}

	public QueryRule addIsNotEmpty(String propertyName) {
		rules.add(new Rule(ISNOTEMPTY, propertyName));
		return this;
	}

	public QueryRule addSql(String sql) {
		if (StringUtils.isNotBlank(sql)) {
			rules.add(new Rule(SQL, sql));
		}
		return this;
	}

	public QueryRule addSubQueryRule(String propertyName) {
		QueryRule queryRule = new QueryRule(propertyName);
		queryRules.add(queryRule);
		return queryRule;
	}
	
	public QueryRule addOr(QueryRule queryRule){
		rules.add(new Rule(OR, queryRule));
		return this;
	}
	
	public QueryRule addNotIn(String propertyName,List<?> values) {
		if (values != null && !values.isEmpty()) {
			rules.add(new Rule(NOTIN, propertyName, new Object[] { values }));
		}
		return this;
	}
	
	public QueryRule addNotIn(String propertyName, Object values[]) {
		if (values != null && values.length != 0) {
			rules.add(new Rule(NOTIN, propertyName, values));
		}
		return this;
	}

	/**
	 * @return the propertyName
	 */
	public String getPropertyName() {
		return propertyName;
	}

	/**
	 * @return the rules
	 */
	public List<Rule> getRules() {
		return rules;
	}

	/**
	 * @return the queryRules
	 */
	public List<QueryRule> getQueryRules() {
		return queryRules;
	}

}
