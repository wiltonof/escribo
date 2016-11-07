package com.escribo.common.security.dataprovider;

import java.util.List;

import com.escribo.common.foundation.dataprovider.IDataProvider;
import com.escribo.common.security.model.ISecurityLevel;

public interface ISecurityLevelDataProvider extends IDataProvider {
	
	public ISecurityLevel loadSecurityLevelByAbbreviation(String abbreviation);
	
	public List<ISecurityLevel> loadListAllSecurityLevels();
		
}
