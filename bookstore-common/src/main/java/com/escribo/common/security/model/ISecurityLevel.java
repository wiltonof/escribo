package com.escribo.common.security.model;

import com.escribo.common.foundation.model.IModel;

public interface ISecurityLevel extends IModel {
	
	public String getDescription();
	
	public void setDescription(String description);
	
	public Long getParentLevel();
	
	public void setParentLevel(Long parentLevel);
	
	public String getAbbreviation();
	
	public void setAbbreviation(String abbreviation);
	
	public Long getParent();
	
	public void setParent(Long parent);
	
}
