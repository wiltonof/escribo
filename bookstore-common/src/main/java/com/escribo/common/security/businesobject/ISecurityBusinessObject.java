package com.escribo.common.security.businesobject;

import com.escribo.common.foundation.businessobject.IBusinessObject;
import com.escribo.common.foundation.model.IModel;
import com.escribo.common.security.model.IPermission;
import com.escribo.common.security.model.IRole;
import com.escribo.common.security.model.IUser;
import com.escribo.common.security.model.IUserAccessData;
import com.escribo.common.security.model.IUserTransaction;

public interface ISecurityBusinessObject extends IBusinessObject<IUser>{
	
	public IUser loadUserByUserName(String userName);

	public IUserAccessData loadPrevUserAccessData();

	public void saveUserAccessData(IUserAccessData userAccessData);
	
	public IUserTransaction saveUserTransaction(String permissionName, IModel primary, IModel secondary, IPermission permission);
	
	public IRole findRoleByDescription(String description);
	
	public IUser getAuthenticatedUser();

}
