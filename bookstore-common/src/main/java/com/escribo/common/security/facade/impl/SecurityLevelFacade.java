package com.escribo.common.security.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.escribo.common.foundation.facade.impl.FacadeImpl;
import com.escribo.common.security.businesobject.ISecurityLevelBusinessObject;
import com.escribo.common.security.facade.ISecurityLevelFacade;
import com.escribo.common.security.model.ISecurityLevel;

@Component
public class SecurityLevelFacade extends FacadeImpl implements ISecurityLevelFacade {
	
	@Autowired
	private ISecurityLevelBusinessObject  securityLevelBusinessObject;
	
	@Override
	public ISecurityLevel findSecurityLevelByAbbreviation(String abbreviation) {
		return securityLevelBusinessObject.findSecurityLevelByAbbreviation(abbreviation);
	}

	@Override
	public List<ISecurityLevel> listSecurityLevel() {
		return securityLevelBusinessObject.listSecurityLevel();
	}

	@Override
	public List<ISecurityLevel> getListVisibleLevels(ISecurityLevel level) {
		return securityLevelBusinessObject.getListVisibleLevels(level);
	}
}