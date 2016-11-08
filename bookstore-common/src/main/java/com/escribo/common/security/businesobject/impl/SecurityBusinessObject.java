package com.escribo.common.security.businesobject.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import com.escribo.common.businessobject.impl.BusinessObjectImpl;
import com.escribo.common.foundation.model.IModel;
import com.escribo.common.security.businesobject.ISecurityBusinessObject;
import com.escribo.common.security.dataprovider.ISecurityDataProvider;
import com.escribo.common.security.model.IPermission;
import com.escribo.common.security.model.IRole;
import com.escribo.common.security.model.ISecurityLevel;
import com.escribo.common.security.model.IUser;
import com.escribo.common.security.model.IUserAccessData;
import com.escribo.common.security.model.IUserTransaction;
import com.escribo.common.security.model.impl.Permission;
import com.escribo.common.security.model.impl.UserTransaction;
import com.escribo.common.util.Pair;

@Component
public class SecurityBusinessObject extends BusinessObjectImpl<IUser> implements ISecurityBusinessObject {

	//@Value("${general.login_permission_cod}")
	private Long loginPermCod=1l;
	
	@Autowired
	private ISecurityDataProvider securityDataProvider;
	
	
	
	public SecurityBusinessObject() {}
	
	public SecurityBusinessObject(ISecurityDataProvider securityDataProvider) {
		this.securityDataProvider = securityDataProvider;
	}
	
	
	@SuppressWarnings("rawtypes")
	@Override
	public List<IModel> loadList(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Class classz) {
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Integer loadRowCount(Map<String, Object> filters, Class classz) {
		return null;
	}

	@Override
	public List<? extends IModel> loadList(IModel filter, Map<String, Pair<Date, Date>> dateFilter, String orderField,	String order) {
		return null;
	}
	

	
	@Override
	public IUser loadUserByUserName(String userName) {
		IUser user = securityDataProvider.loadUserByUserName(userName);
		if (user != null && user.getRole().getPermissions().contains(securityDataProvider.loadById(Permission.class, loginPermCod))) {
			return user;
		} else {
			throw new BadCredentialsException(null);
		}
	}

	@Override
	public IUserAccessData loadPrevUserAccessData() {
		IUserAccessData aux = securityDataProvider.loadPrevUserAccessData(getAuthenticatedUser().getId());
		if (aux != null) {
			aux.setAccessCounter(securityDataProvider.loadUserAccessNumber(getAuthenticatedUser().getId()));
		}
		return aux;
	}

	@Override
	public void saveUserAccessData(IUserAccessData userAccessData) {	
		if (userAccessData.getIp() == null) {
			userAccessData.setIp("0.0.0.0");
		}
		securityDataProvider.saveOrUpdate(userAccessData);	
	}
	
	@Override
	public IUserTransaction saveUserTransaction(String permissionName, IModel primary, IModel secondary, IPermission permission) {
		IUserTransaction transaction = new UserTransaction();
		transaction.setPermission(permission);
		transaction.setUser(this.getAuthenticatedUser());
		if (primary != null) {
			transaction.setPrimaryId(primary.getId());
			if (primary instanceof IUser) {
				transaction.setPrimaryCod(((IUser) primary).getName());
			} else if (primary instanceof IRole) {
				transaction.setPrimaryCod(((IRole) primary).getDescription());
			} else if (primary instanceof ISecurityLevel) {
				transaction.setPrimaryCod(((ISecurityLevel) primary).getAbbreviation());
			}  
		}

		if (secondary != null) {
			transaction.setSecondaryId(secondary.getId());
			if (secondary instanceof IUser) {
				transaction.setSecondaryCod(((IUser) secondary).getName());
			} else if (secondary instanceof IRole) {
				transaction.setSecondaryCod(((IRole) secondary).getDescription());
			} else if (secondary instanceof ISecurityLevel) {
				transaction.setSecondaryCod(((ISecurityLevel) secondary).getAbbreviation());
			} 
		}
		return (IUserTransaction) securityDataProvider.saveOrUpdate(transaction);
	}

	@Override
	public IRole findRoleByDescription(String description) {
		// TODO Auto-generated method stub
		return securityDataProvider.findRoleByDescription(description);
	}



	
}
