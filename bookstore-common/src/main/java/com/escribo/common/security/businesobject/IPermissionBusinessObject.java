package com.escribo.common.security.businesobject;

import java.util.List;

import com.escribo.common.businessobject.IBusinessObject;
import com.escribo.common.security.model.IPermission;

public interface IPermissionBusinessObject extends IBusinessObject<IPermission> {

	
	public List<IPermission> getListPermissions();

	public IPermission getPermissionByName(String name);
}
