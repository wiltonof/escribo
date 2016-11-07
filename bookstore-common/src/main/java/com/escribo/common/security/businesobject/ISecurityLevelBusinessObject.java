package com.escribo.common.security.businesobject;

import java.util.List;

import com.escribo.common.businessobject.IBusinessObject;
import com.escribo.common.security.model.ISecurityLevel;

public interface ISecurityLevelBusinessObject extends IBusinessObject<ISecurityLevel> {
	public List<ISecurityLevel> getListVisibleLevels(ISecurityLevel level);

	public ISecurityLevel findSecurityLevelByAbbreviation(String abbreviation);
	
	public List<ISecurityLevel> listSecurityLevel();
}
