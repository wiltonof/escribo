package com.escribo.common.foundation.model.impl;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.escribo.common.foundation.model.IModel;

@SuppressWarnings("serial")
@MappedSuperclass
public class AbstractModel implements IModel {

	private Long id;
	
	private Date createdAt;
		
	private Date updatedAt;	
	
	private boolean enabled;
	
	private boolean deleted;

	@Id
	@Column(name = "id", columnDefinition="bigint", nullable=false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "created_at", columnDefinition="datetime")
	@Temporal(TemporalType.TIMESTAMP)
	@Override
	public Date getCreatedAt() {
		return createdAt;
	}

	@Override
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@Column(name = "updated_at", columnDefinition="datetime")
	@Temporal(TemporalType.TIMESTAMP)
	@Override
	public Date getUpdatedAt() {
		return updatedAt;
	}

	@Override
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Column(name = "enabled", columnDefinition="tinyint(1) not null default 1")
	@Override
	public boolean isEnabled() {
		return this.enabled;
	}
	

	@Transient
	@Override
	public boolean getEnabled() {
		return isEnabled();
	}

	@Override
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	@Column(name = "deleted", columnDefinition="tinyint(1) not null default 1")
	@Override
	public boolean isDeleted() {
		return this.deleted;
	}
	
	@Transient
	@Override
	public boolean getDeleted() {
		return isDeleted();		
	}	
	
	@Override
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	


	@Override
	public IModel cloneModel() {
		try {
			IModel clone = (IModel) super.clone();
			return clone;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean validate() {
		return false;
	}

}
