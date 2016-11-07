package com.escribo.common.security.model.impl;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.springframework.security.core.GrantedAuthority;

import com.escribo.common.foundation.model.impl.AbstractModel;
import com.escribo.common.security.model.IRole;
import com.escribo.common.security.model.ISecurityLevel;
import com.escribo.common.security.model.IUser;



@SuppressWarnings("deprecation")
@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = { "id", "username", "email" }) })
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class User extends AbstractModel implements IUser {
	private static final long serialVersionUID = -1983797012470522238L;

    private String email;    
    private String password;
	transient private String confirmPassword;
	private String passwordToken;
	private boolean changePassword;
    private String name;
    private String username;
    private IRole role;
    
    private List<ISecurityLevel> securityLevels = new ArrayList<ISecurityLevel>();
	

	@Column(name="username", unique=true, nullable=false, length=100)
	@Override
	public String getUsername() {
		return this.username;
	}
	
	@Override
	public void setUsername(String username) {
		this.username = username;
	}
	
	@Column(name="email", unique=true, nullable=false, length=100)
	@Override
	public String getEmail() {
		return this.email;
	}

	@Override
	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name="password",nullable=false,length=150)
	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public void setPassword(String password) {
		this.password = password;
	}

	@Transient
	@Override
	public String getConfirmPassword() {
		return confirmPassword;
	}

	@Transient
	@Override
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	@Column(name="password_token", nullable=true, length=250)
	@Override
	public String getPasswordToken() {
		return passwordToken;
	}
	
	@Override
	public void setPasswordToken(String passwordToken) {
		this.passwordToken = passwordToken;
	}
	
	@Column(name="change_password", nullable=false, columnDefinition="tinyint(1) not null default 0")
	@Override
	public boolean isChangePassword() {
		return changePassword;
	}
	
	@Override
	public void setChangePassword(boolean changePassword) {
		this.changePassword = changePassword;
	}	
	
	@Column(name="name",nullable=false,length=100)
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.DETACH, CascadeType.REFRESH }, targetEntity = Role.class)
	@JoinColumn(name = "role_id", nullable = false)
	@Override
	public IRole getRole() {
		return role;
	}

	public void setRole(IRole role) {
		this.role = role;
	}
	
	@Transient
	@Override
	public List<ISecurityLevel> getSecurityLevels() {
		return securityLevels;
	}

	@Transient
	private void setSecurityLevels(List<ISecurityLevel> securityLevels) {
		this.securityLevels = securityLevels;
	}	
	
	@Transient
	@Override
	public void addAllSecurityLevels(List<ISecurityLevel> securityLevels) {
		this.securityLevels.addAll(securityLevels);
	}
	
	@Override
	public void addSecurityLevels(ISecurityLevel securityLevel) {
		this.securityLevels.add(securityLevel);		
	}

	@Override
	@Transient
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	@Transient
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	@Transient
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	@Override
	@Transient
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.getRole().getPermissions();
	}




	
}
