package com.escribo.common.security.model.impl;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.escribo.common.foundation.model.impl.AbstractModel;
import com.escribo.common.security.model.IPermission;
import com.escribo.common.security.model.IRole;
import com.escribo.common.security.model.ISecurityLevel;



@SuppressWarnings("deprecation")
@Entity
@Table(name = "role", uniqueConstraints = { @UniqueConstraint(columnNames = { "id", "description" }) })
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class Role extends AbstractModel implements IRole {
	private static final long serialVersionUID = -7787049741786068885L;
	
	private String description;
	private ISecurityLevel securityLevel;
	private List<IPermission> permissions;
	private String listRolesAllowedUserCreate;
	private String listRolesAllowedUserModificate;
	private String listRolesAllowedUserDelete;
	private String listRolesAllowedUserView;
	
	
	@Column(name = "description", length = 250)
	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}
	
		
	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.DETACH, CascadeType.REFRESH }, targetEntity = SecurityLevel.class)
	@JoinColumn(name = "security_level_id", nullable = false)	
	@Override
	public ISecurityLevel getSecurityLevel() {
		return securityLevel;
	}
		
	@Override
	public void setSecurityLevel(ISecurityLevel securityLevel) {
		this.securityLevel = securityLevel;
	}
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.DETACH, targetEntity = Permission.class)
	@JoinTable(name = "role_has_permission", joinColumns = { @JoinColumn(name = "role_id", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "permission_id", nullable = false) })
	@Fetch(FetchMode.SUBSELECT)
	@Override
	public List<IPermission> getPermissions() {
		return permissions;
	}
	
	@Override
	public void setPermissions(List<IPermission> permissions) {
		this.permissions = permissions;
	}
	
	@Column(name = "list_allow_create_user")
	@Override
	public String getListRolesAllowedUserCreate() {
		return listRolesAllowedUserCreate;
	}
	
	@Override
	public void setListRolesAllowedUserCreate(String listRolesAllowedUserCreate) {
		this.listRolesAllowedUserCreate = listRolesAllowedUserCreate;
	}
	
	@Column(name = "list_allow_edite_user")
	@Override
	public String getListRolesAllowedUserModificate() {
		return listRolesAllowedUserModificate;
	}
	
	@Override
	public void setListRolesAllowedUserModificate(String listRolesAllowedUserModificate) {
		this.listRolesAllowedUserModificate = listRolesAllowedUserModificate;
	}
	
	@Column(name = "list_allow_delete_user")
	@Override
	public String getListRolesAllowedUserDelete() {
		return listRolesAllowedUserDelete;
	}
	
	@Override
	public void setListRolesAllowedUserDelete(String listRolesAllowedUserDelete) {
		this.listRolesAllowedUserDelete = listRolesAllowedUserDelete;
	}
	
	@Column(name = "list_allow_show_user")
	@Override
	public String getListRolesAllowedUserView() {
		return listRolesAllowedUserView;
	}
	
	@Override
	public void setListRolesAllowedUserView(String listRolesAllowedUserView) {
		this.listRolesAllowedUserView = listRolesAllowedUserView;
	}


}
