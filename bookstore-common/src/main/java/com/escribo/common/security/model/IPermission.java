package com.escribo.common.security.model;

import org.springframework.security.core.GrantedAuthority;

import com.escribo.common.foundation.model.IModel;



public interface IPermission extends IModel , GrantedAuthority, Comparable<IPermission> {

	public String getDescription();

	public void setDescription(String description);

	public String getObservation();

	public void setObservation(String observation);
	
}
