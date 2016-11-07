package com.escribo.common.security.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.escribo.common.foundation.model.IModel;
import com.escribo.common.security.businesobject.ISecurityBusinessObject;
import com.escribo.common.security.facade.IPermissionFacade;
import com.escribo.common.security.facade.ISecurityFacade;
import com.escribo.common.security.facade.ISecurityLevelFacade;
import com.escribo.common.security.model.IRole;
import com.escribo.common.security.model.IUser;
import com.escribo.common.security.model.IUserAccessData;
import com.escribo.common.security.model.IUserTransaction;

@Component
public class SecurityFacade implements ISecurityFacade {

	@Autowired
	private ISecurityBusinessObject securityBusinessObject;
	
	@Autowired
	private IPermissionFacade permissionFacade;
	
	@Autowired
	private ISecurityLevelFacade securityLevelFacade;

	
	
	public SecurityFacade() {
	
	}
	

	public SecurityFacade(ISecurityBusinessObject securityBusinessObject, IPermissionFacade permissionFacade, ISecurityLevelFacade securityLevelFacade ) {
		this.securityBusinessObject = securityBusinessObject;
		this.permissionFacade 		= permissionFacade;
		this.securityLevelFacade 	= securityLevelFacade;
	}
	
	public SecurityFacade(ISecurityBusinessObject securityBusinessObject, IPermissionFacade permissionFacade) {
		this.securityBusinessObject = securityBusinessObject;
		this.permissionFacade = permissionFacade;
	}
	
	public IUser loadUserByUsername(String username) {
		IUser user = securityBusinessObject.loadUserByUserName(username);	
		if (user != null) {
			user.addAllSecurityLevels(securityLevelFacade.getListVisibleLevels(user.getRole().getSecurityLevel()));
		}
		return user;
	}

	@Override
	public IUserAccessData loadPrevUserAccessData() {
		return securityBusinessObject.loadPrevUserAccessData();
	}

	@Override
	public void saveUserAccessData(IUserAccessData userAccessData) {		
		securityBusinessObject.saveUserAccessData(userAccessData);
	}

	@Override
	public void updateSelfUser(IUser aux) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void recoverPassword(String userName, String userEmail) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IUserTransaction saveUserTransaction(String permissionName, IModel primary, IModel secondary) {
		
		return securityBusinessObject.saveUserTransaction(permissionName, primary, secondary, permissionFacade.getPermissionByName(permissionName));
	}


	@Override
	public IRole findRoleByDescription(String description) {
		return securityBusinessObject.findRoleByDescription(description);
	}





}
