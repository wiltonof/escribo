package com.escribo.common.security.businesobject.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import com.escribo.common.foundation.businessobject.impl.BusinessObjectImpl;
import com.escribo.common.security.businesobject.IPermissionBusinessObject;
import com.escribo.common.security.model.IPermission;

@Component
public class PermissionBusinessObject extends BusinessObjectImpl<IPermission> implements IPermissionBusinessObject {
	
	@Override
	public IPermission getPermissionByName(String name) {
		IPermission permission = null;
		for (IPermission aux : this.getListPermissions()) {
			if (aux.getAuthority().equals(name)) {
				permission = aux;
				break;
			}
		}
		return permission;
	}
	
	@Override
	public List<IPermission> getListPermissions() {
		List<IPermission> result = this.getAuthenticatedUser().getRole().getPermissions();
		if (result != null) {
			Collections.sort(result);
		}
		return result;
	}
}
