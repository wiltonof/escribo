package com.escribo.common.security.model;
import java.util.List;

import com.escribo.common.foundation.model.IModel;



public interface IRole extends IModel {

	public String getDescription();

	public void setDescription(String description);
	
	public ISecurityLevel getSecurityLevel();

	public void setSecurityLevel(ISecurityLevel securityLevel);
	
	public String getListRolesAllowedUserCreate();

	public void setListRolesAllowedUserCreate(String listRolesAllowedUserCreate);

	public String getListRolesAllowedUserModificate();

	public void setListRolesAllowedUserModificate(String listRolesAllowedUserModificate);

	public String getListRolesAllowedUserDelete();

	public void setListRolesAllowedUserDelete(String listRolesAllowedUserDelete);

	public String getListRolesAllowedUserView();

	public void setListRolesAllowedUserView(String listRolesAllowedUserView);

	public List<IPermission> getPermissions();

	public void setPermissions(List<IPermission> permissions);
}