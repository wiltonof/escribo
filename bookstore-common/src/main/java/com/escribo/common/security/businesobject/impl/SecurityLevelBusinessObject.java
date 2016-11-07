package com.escribo.common.security.businesobject.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.escribo.common.businessobject.impl.BusinessObjectImpl;
import com.escribo.common.security.businesobject.ISecurityLevelBusinessObject;
import com.escribo.common.security.dataprovider.ISecurityLevelDataProvider;
import com.escribo.common.security.model.ISecurityLevel;

@Component
public class SecurityLevelBusinessObject extends BusinessObjectImpl<ISecurityLevel> implements ISecurityLevelBusinessObject {
	
	@Autowired
	private ISecurityLevelDataProvider securityLevelDataProvider;
	
	@Override
	public List<ISecurityLevel> getListVisibleLevels(ISecurityLevel level) {
		List<ISecurityLevel> listVisibleLevels = new ArrayList<ISecurityLevel>();
		List<ISecurityLevel> listAllLevels = this.loadListAllLevels();

		// Adding visibility for non-leveled registers. ADMs only.
		if (level.getId() == 1) {
			for (ISecurityLevel aux : listAllLevels) {
				if (aux.getId() == 0) {
					listVisibleLevels.add(aux);
				}
			}
		}

		listVisibleLevels.add(level);

		listVisibleLevels.addAll(getListLowerLevels(level, listAllLevels, ""));

		return listVisibleLevels;
	}
	
	private List<ISecurityLevel> loadListAllLevels() {
		return securityLevelDataProvider.loadListAllSecurityLevels();
	}

	private List<ISecurityLevel> getListLowerLevels(ISecurityLevel level, List<ISecurityLevel> listAllLevels, String identParent) {

		String ident = identParent + "- ";
		List<ISecurityLevel> result = new ArrayList<ISecurityLevel>();

		for (ISecurityLevel aux : listAllLevels) {
			if (aux.getParent() == level.getId()) {
				aux.setAbbreviation(ident + aux.getAbbreviation());
				result.add(aux);
				result.addAll(this.getListLowerLevels(aux, listAllLevels, ident));
			}
		}

		return result;
	}
	
	@Override
	public ISecurityLevel findSecurityLevelByAbbreviation(String abbreviation) {
		return securityLevelDataProvider.loadSecurityLevelByAbbreviation(abbreviation);
	}

	@Override
	public List<ISecurityLevel> listSecurityLevel() {
		return securityLevelDataProvider.loadListAllSecurityLevels();
	}

}
