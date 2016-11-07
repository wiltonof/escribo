package com.escribo.common.foundation.dataprovider;

import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.primefaces.model.SortOrder;

import com.escribo.common.foundation.model.IModel;
import com.escribo.common.security.model.IRole;
import com.escribo.common.security.model.ISecurityLevel;

@SuppressWarnings("rawtypes")
public interface IDataProvider {

	public Object saveOrUpdate(Object entity);
	
	
	public Object loadById( Class classz, Long id);

	public void delete(Object entity);

	public SessionFactory getSessionFactory();

	public void setSessionFactory(SessionFactory sessionFactory);

	public List<IModel> loadList(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Class classz,List<ISecurityLevel> visibleSecurityLevels,  List<IRole> listVisibleRoles);

	public Integer loadRowCount(Map<String, Object> filters, Class classz,List<ISecurityLevel> visibleSecurityLevels,  List<IRole> listVisibleRoles);

	public void reattach(Object entity);

	public void detach(Object entity);
	
	public List<IModel> loadList(Class classz);
	
	public void initializeLazyList(Object list);
	
}
