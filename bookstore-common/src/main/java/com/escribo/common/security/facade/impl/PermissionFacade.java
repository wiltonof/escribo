package com.escribo.common.security.facade.impl;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.escribo.common.foundation.model.IModel;
import com.escribo.common.security.businesobject.IPermissionBusinessObject;
import com.escribo.common.security.facade.IPermissionFacade;
import com.escribo.common.security.model.IPermission;

@Component
public class PermissionFacade  implements IPermissionFacade {

	@Autowired
	private IPermissionBusinessObject permissionBusinessObject;

	@Override
	public List<IPermission> getListPermissions() {
		return permissionBusinessObject.getListPermissions();
	}

	@Override
	public IPermission getPermissionByName(String name) {
		return permissionBusinessObject.getPermissionByName(name);
	}

	@Override
	public List<IModel> loadList(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Class classz) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer loadRowCount(Map<String, Object> filters, Class classz) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
