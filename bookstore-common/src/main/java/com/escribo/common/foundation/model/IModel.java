package com.escribo.common.foundation.model;

import java.io.Serializable;

import java.util.Date;

public interface IModel extends Serializable, Cloneable {
	
	public static final String CREATE	=	"CREATE";
	public static final String UPDATE	=	"UPDATE";
	public static final String VIEW		=	"VIEW";
	public static final String DELETE	=	"DELETE";
	
	public Long getId();
	
	public void setId(Long id);

	public Date getCreatedAt();
	
	public void setCreatedAt(Date createdAt);

	public Date getUpdatedAt();

	public void setUpdatedAt(Date updatedAt);
	
	public boolean isEnabled();

	public boolean getEnabled();
	
	public void setEnabled(boolean enable);

	public void setDeleted(boolean deleted);
	
	public boolean getDeleted();
	
	public boolean isDeleted();

	public boolean isSystem();

	public boolean getSystem();

	public void setSystem(boolean deleted);
	
	public IModel cloneModel();
	
	public boolean validate();
}
