package com.escribo.common.security.dataprovider;

import java.util.List;

import com.escribo.common.foundation.dataprovider.IDataProvider;
import com.escribo.common.security.model.IRole;
import com.escribo.common.security.model.IUser;
import com.escribo.common.security.model.IUserAccessData;

public interface ISecurityDataProvider extends IDataProvider {
	public IUser loadUserByUserName(String userName);

	public IUserAccessData loadPrevUserAccessData(Long id);

	public Integer loadUserAccessNumber(Long id);
	
	public List<IRole> loadListRoles(List<Integer> listVisibleRoles);
	
	public IRole findRoleByDescription(String description);
	
	
}
