package com.escribo.common.security.facade.impl;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.escribo.common.foundation.model.IModel;
import com.escribo.common.security.businesobject.ISecurityLevelBusinessObject;
import com.escribo.common.security.facade.ISecurityLevelFacade;
import com.escribo.common.security.model.ISecurityLevel;

@Component
public class SecurityLevelFacade  implements ISecurityLevelFacade {
	
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

	@Override
	public List<IModel> loadList(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Class classz) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer loadRowCount(Map<String, Object> filters, Class classz) {
		// TODO Auto-generated method stub
		return null;
	}
}