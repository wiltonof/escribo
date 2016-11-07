package com.escribo.common.security.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.escribo.common.foundation.facade.impl.FacadeImpl;
import com.escribo.common.security.businesobject.IPermissionBusinessObject;
import com.escribo.common.security.facade.IPermissionFacade;
import com.escribo.common.security.model.IPermission;

@Component
public class PermissionFacade extends FacadeImpl implements IPermissionFacade {

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
	
}
