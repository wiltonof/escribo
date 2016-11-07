package com.escribo.common.security.model.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.escribo.common.foundation.model.impl.AbstractModel;
import com.escribo.common.security.model.IUserAccessData;

@SuppressWarnings("deprecation")
@Entity
@Table(name = "user_access_data")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class UserAccessData extends AbstractModel implements IUserAccessData {
	private static final long serialVersionUID = -1075735856466917053L;
	
	private Long userId;
	private String ip;
	transient private Integer accessCounter;
	
	@Column(name = "user_id", nullable = false)
	@Override
	public Long getUserId() {
		return this.userId;
	}

	@Override
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "ip_request", length = 39, nullable = false)
	@Override
	public String getIp() {
		return ip;
	}

	@Override
	public void setIp(String ip) {
		this.ip = ip;
	}

	@Transient
	@Override
	public Integer getAccessCounter() {
		return accessCounter;
	}

	@Transient
	@Override
	public void setAccessCounter(Integer accessCounter) {
		this.accessCounter = accessCounter;
	}	

}
