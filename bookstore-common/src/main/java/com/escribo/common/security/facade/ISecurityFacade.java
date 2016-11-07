package com.escribo.common.security.facade;

import com.escribo.common.foundation.model.IModel;
import com.escribo.common.security.model.IRole;
import com.escribo.common.security.model.IUser;
import com.escribo.common.security.model.IUserAccessData;
import com.escribo.common.security.model.IUserTransaction;

public interface ISecurityFacade {
	public IUser loadUserByUsername(String username);
	
	public IUserAccessData loadPrevUserAccessData();
	
	public void saveUserAccessData(IUserAccessData userAccessData);

	public void updateSelfUser(IUser aux);

	public void recoverPassword(String userName, String userEmail);
	
	public IUserTransaction saveUserTransaction(String permissionName, IModel primary, IModel secondary);
		
	public IRole findRoleByDescription(String description);
	
}
