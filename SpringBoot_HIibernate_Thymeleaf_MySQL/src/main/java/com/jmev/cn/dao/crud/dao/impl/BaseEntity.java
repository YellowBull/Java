package com.jmev.cn.dao.crud.dao.impl;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * 实体对象基类
 * @author 邵欣
 */
@MappedSuperclass
public abstract class BaseEntity implements Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7381601484906811004L;

	/** 主键· */
	@Id
	@Column(name = "ID", length=64)
	@GeneratedValue(generator = "custom_id_generate")
	@GenericGenerator(name = "custom_id_generate", strategy = "com.jmev.cn.dao.crud.dao.generator.CustomIdentifierGenerator")
	protected String id;

	/** 首次插入时间 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FIRST_INSERT")
	protected Date firstInsert;

	/** 最后修改时间 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_MODIFIED")
	protected Date lastModified;

	/** 创建人 */
	@Column(name = "CREATE_USER", length=64)
	protected String createUser;

	/** 修改人 */
	@Column(name = "UPDATE_USER", length=64)
	protected String updateUser;

	/** 是否已删除 */
	@Column(name = "IS_DELETED")
	protected Boolean isDeleted = Boolean.FALSE;

	/** 删除原因 */
	@Column(name = "DEL_REASON", length=255)
	protected String delReason;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the firstInsert
	 */
	public Date getFirstInsert() {
		return firstInsert;
	}

	/**
	 * @param firstInsert the firstInsert to set
	 */
	public void setFirstInsert(Date firstInsert) {
		this.firstInsert = firstInsert;
	}

	/**
	 * @return the lastModified
	 */
	public Date getLastModified() {
		return lastModified;
	}

	/**
	 * @param lastModified the lastModified to set
	 */
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	/**
	 * @return the createUser
	 */
	public String getCreateUser() {
		return createUser;
	}

	/**
	 * @param createUser the createUser to set
	 */
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	/**
	 * @return the updateUser
	 */
	public String getUpdateUser() {
		return updateUser;
	}

	/**
	 * @param updateUser the updateUser to set
	 */
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	/**
	 * @return the isDeleted
	 */
	public Boolean getIsDeleted() {
		return isDeleted;
	}

	/**
	 * @param isDeleted the isDeleted to set
	 */
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	/**
	 * @return the delReason
	 */
	public String getDelReason() {
		return delReason;
	}

	/**
	 * @param delReason the delReason to set
	 */
	public void setDelReason(String delReason) {
		this.delReason = delReason;
	}
	
	
	
}
