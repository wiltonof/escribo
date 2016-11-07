package com.escribo.common.security.facade;

import java.util.List;

import com.escribo.common.foundation.facade.IFacade;
import com.escribo.common.security.model.ISecurityLevel;

public interface ISecurityLevelFacade extends IFacade {
	
	
	public List<ISecurityLevel> getListVisibleLevels(ISecurityLevel level);
	
	public ISecurityLevel findSecurityLevelByAbbreviation(String abbreviation);
	
	public List<ISecurityLevel> listSecurityLevel();
}
