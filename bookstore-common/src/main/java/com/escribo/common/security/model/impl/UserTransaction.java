package com.escribo.common.security.model.impl;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.escribo.common.foundation.model.impl.AbstractModel;
import com.escribo.common.security.model.IPermission;
import com.escribo.common.security.model.IUser;
import com.escribo.common.security.model.IUserTransaction;



@SuppressWarnings("deprecation")
@Entity
@Table(name = "user_transaction", uniqueConstraints = { @UniqueConstraint(columnNames =  "id") })
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class UserTransaction extends AbstractModel implements IUserTransaction {
	private static final long serialVersionUID = -6106658963689091753L;

	private IUser user;
	private IPermission permission;
	private Long primaryId;
	private String primaryCod;
	private Long secondaryId;
	private String secondaryCod;

	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.DETACH, CascadeType.REFRESH }, targetEntity = User.class)
	@JoinColumn(name = "user_id", nullable = false)
	@NotFound(action=NotFoundAction.IGNORE)
	@Override
	public IUser getUser() {
		return user;
	}

	@Override
	public void setUser(IUser user) {
		this.user = user;
	}

	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.DETACH, CascadeType.REFRESH }, targetEntity = Permission.class)
	@JoinColumn(name = "permission_id", nullable = false)
	@Override
	public IPermission getPermission() {
		return permission;
	}

	@Override
	public void setPermission(IPermission permission) {
		this.permission = permission;
	}

	@Column(name = "primary_id", nullable = true)
	@Override
	public Long getPrimaryId() {
		return this.primaryId;
	}

	@Override
	public void setPrimaryId(Long primaryId) {
		this.primaryId = primaryId;
	}

	@Column(name = "primary_cod", nullable = true, length = 100)
	@Override
	public String getPrimaryCod() {
		return primaryCod;
	}

	@Override
	public void setPrimaryCod(String primaryCod) {
		this.primaryCod = primaryCod;
	}

	@Column(name = "secundary_id", nullable = true)
	@Override
	public Long getSecondaryId() {
		return secondaryId;
	}

	@Override
	public void setSecondaryId(Long secondaryId) {
		this.secondaryId = secondaryId;
	}

	@Column(name = "secundary_cod", nullable = true, length = 50)
	@Override
	public String getSecondaryCod() {
		return secondaryCod;
	}

	@Override
	public void setSecondaryCod(String secondaryCod) {
		this.secondaryCod = secondaryCod;
	}

}
