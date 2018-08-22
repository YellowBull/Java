package com.jmev.cn.dao.crud.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.jmev.cn.constant.CarinfoConstant;

/**
 * 分页帮助类
 * @author 邵欣
 */
public class Page implements Serializable {
	private static final long serialVersionUID = 3208035777667362034L;
	
	/** 默认每页记录数 */
	public static final int DEFAULT_PAGE_SIZE = CarinfoConstant.PAGE_SIZE;
	
	/**默认当前页*/
	public static final int DEFAULT_PAGE_INDEX = CarinfoConstant.PAGE_INDEX;

	/** 总记录数 */
	private int totalCount;

	/** 每页记录数 */
	private int pageSize;

	/** 当前页索引 */
	private int pageIndex;
	
	/**
	 *  总页数
	 */
    private int pageCount;
    
	private List<Object> result = new ArrayList<Object>();

	/**
	 * @param pageSize
	 * @param pageIndex
	 */
	public Page(int pageSize, int pageIndex) {
		this.pageSize = (pageSize == 0) ? DEFAULT_PAGE_SIZE : pageSize;
		this.pageIndex = pageIndex == 0 ? DEFAULT_PAGE_INDEX : pageIndex ;
	}

	/**
	 * @return the totalCount
	 */
	public int getTotalCount() {
		return totalCount;
	}

	/**
	 * @param totalCount
	 *            the totalCount to set
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize
	 *            the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return the pageIndex
	 */
	public int getPageIndex() {
		return pageIndex;
	}

	/**
	 * @param pageIndex
	 *            the pageIndex to set
	 */
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	/**
	 * @return the result
	 */
	public List<Object> getResult() {
		return result;
	}

	/**
	 * @param result
	 *            the result to set
	 */
	public void setResult(List<Object> result) {
		this.result = result;
	}

	public void addResult(Object object) {
		if (result == null) {
			result = new ArrayList<Object>();
		}
		result.add(object);
	}

	/**
	 * 根据每页记录数和当前页索引返回当前页首条记录在数据库里的位置
	 * 
	 * @return
	 */
	public int getRecordIndex() {
		return (pageIndex - 1) * pageSize;
	}

	/**
	 * 获取总的页数
	 * 
	 * @return
	 */
	public int getTotalPage() {
		if (totalCount % pageSize == 0) {
			return totalCount / pageSize;
		} else {
			return totalCount / pageSize + 1;
		}
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	
	

}
