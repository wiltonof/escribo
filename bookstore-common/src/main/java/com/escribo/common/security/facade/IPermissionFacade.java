package com.escribo.common.security.facade;

import java.util.List;

import com.escribo.common.foundation.facade.IFacade;
import com.escribo.common.security.model.IPermission;

public interface IPermissionFacade extends IFacade {
	
	public List<IPermission> getListPermissions();
	
	public IPermission getPermissionByName(String name);
}
