package com.escribo.common.foundation.dataprovider.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hibernate.type.StringType;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.escribo.common.foundation.dataprovider.IDataProvider;
import com.escribo.common.foundation.model.FieldName;
import com.escribo.common.foundation.model.IModel;
import com.escribo.common.security.model.IRole;
import com.escribo.common.security.model.ISecurityLevel;

@Transactional
public abstract class DataProviderImpl implements IDataProvider {

	
	@Autowired
	protected SessionFactory sessionFactory;
	


	@Override
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	@Override
	public Object saveOrUpdate(Object entity) {
		try{
			
			if (((IModel) entity).getId() == null) {
				getSessionFactory().getCurrentSession().save(entity);
			} else {
				entity = getSessionFactory().getCurrentSession().merge(entity);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return entity;
	}

	@SuppressWarnings("rawtypes") 
	@Override
	public Object loadById(Class classz, Long id) {
		if (classz != null) {
			Criteria criteria = getSessionFactory().getCurrentSession().createCriteria((Class) classz);
			criteria.add(
					Restrictions.and(
							Restrictions.eq(FieldName.ID.getValue(), id),
							Restrictions.eq(FieldName.DELETED.getValue(), false),
							Restrictions.eq(FieldName.ENABLED.getValue(), true)
					)
			);	
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			Object result = criteria.uniqueResult();
			return result;
		} else {
			return null;
		}
	}

	@Override
	public void delete(Object entity) {
		getSessionFactory().getCurrentSession().delete(entity);
	}

	@Override
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<IModel> loadList(Class classz){
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(classz);
		return criteria.list();		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" }) 
	@Override
	public List<IModel> loadList(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filtersOriginal, Class classz,List<ISecurityLevel> visibleSecurityLevels, List<IRole> listVisibleRoles) {
		try{
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(classz);
		criteria.setFirstResult(first);
		criteria.setMaxResults(pageSize);
		Map<String, Object> filters = new HashMap<String, Object>();
		if (filtersOriginal != null) {
			filters.putAll(filtersOriginal);
			filters.put(FieldName.DELETED.getValue(), false);
			filters.put(FieldName.ENABLED.getValue(), true);
		}

		Map<String, Object> auxAlias = new HashMap<String, Object>();
		int auxCountForAliasName = 0;

		List<String> auxAliasSort = Arrays.asList(sortField.split("\\."));
		if (auxAliasSort != null && auxAliasSort.size() > 1) {
			Iterator<String> iterator = auxAliasSort.iterator();
			String prevAlias = null;
			do {
				String field = iterator.next();
				if (iterator.hasNext()) {
					if (prevAlias == null) {
						criteria.createAlias(field, field, JoinType.LEFT_OUTER_JOIN);
						auxAlias.put(field, field);
					} else {
						criteria.createAlias(prevAlias + "." + field, field, JoinType.LEFT_OUTER_JOIN);
						auxAlias.put(field, prevAlias + "." + field);
					}
					prevAlias = field;
				} else {
					sortField = prevAlias + "." + field;
				}
			} while (iterator.hasNext());
		}

		if (sortOrder == SortOrder.DESCENDING) {
			criteria.addOrder(Order.desc(sortField));
		} else {
			criteria.addOrder(Order.asc(sortField));
		}
		

		if (filters != null && filters.size() > 0) {
			Set<String> auxSet = filters.keySet();
			for (String auxStr : auxSet) {
				//workaround to show terminals offline without last communication date
				if (!auxStr.contains("isNull") && !auxStr.contains("notEqual")) {
					List<String> auxAliasFilter = Arrays.asList(auxStr.split("\\."));
					if (auxAliasFilter != null && auxAliasFilter.size() > 1) {
						Iterator<String> iterator = auxAliasFilter.iterator();
						String prevAlias = null;
						do {
							String field = iterator.next();
							if (iterator.hasNext()) {
								if (prevAlias == null) {
									if (!auxAlias.keySet().contains(field)) {
										criteria.createAlias(field, field);
										auxAlias.put(field, field);
									}
								} else {
									if (!auxAlias.keySet().contains(field)) {
										criteria.createAlias(prevAlias + "." + field, field);
										auxAlias.put(field, prevAlias + "." + field);
									} else {
										if (!auxAlias.get(field).equals(prevAlias + "." + field)) {
											criteria.createAlias(prevAlias + "." + field, field + auxCountForAliasName);
											auxAlias.put(field + auxCountForAliasName, prevAlias + "." + field);

											field = field + auxCountForAliasName;
											auxCountForAliasName++;
										}
									}
								}
								prevAlias = field;
							} else {
								Class fieldClass = null;
								try {
									for (String aux : auxAliasFilter) {
										String getMethod = "get" + aux.substring(0, 1).toUpperCase() + aux.substring(1);
										if (fieldClass == null) {
											fieldClass = classz.getMethod(getMethod).getReturnType();
										} else {
											fieldClass = fieldClass.getMethod(getMethod).getReturnType();
										}
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
								
								if (fieldClass != null && fieldClass.isEnum()) {
									if (auxSet.contains(auxStr+"_notEquals")) {
										criteria.add(Restrictions.or(Restrictions.isNull(prevAlias + "." + field), Restrictions.ne(prevAlias + "." + field, Enum.valueOf(fieldClass, filters.get(auxStr).toString()))));
									} else {
										criteria.add(Restrictions.eq(prevAlias + "." + field, Enum.valueOf(fieldClass, filters.get(auxStr).toString())));
									}
								} else {
									criteria.add(Restrictions.like(prevAlias + "." + field, "%" + filters.get(auxStr) + "%"));
								}
							}
						} while (iterator.hasNext());
					} else {
						String getMethod = "get" + auxStr.substring(0, 1).toUpperCase() + auxStr.substring(1);
						Class fieldClass = null;
						try {
							fieldClass = classz.getMethod(getMethod).getReturnType();
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						if (fieldClass != null && fieldClass.equals(String.class)) {
							if (auxStr.equals("serialNumber")){
								criteria.add(Restrictions.sqlRestriction("REVERSE({alias}.num_serie) like REVERSE(?)", "%" + filters.get(auxStr), StringType.INSTANCE));
							} else if (filters.get(auxStr).toString().length() == 0) {
								criteria.add(Restrictions.eq(auxStr, filters.get(auxStr)));
							} else {						
								criteria.add(Restrictions.like(auxStr, "%" + filters.get(auxStr) + "%"));
							}
						} else if (fieldClass != null && fieldClass.isEnum()) {
							if (auxSet.contains(auxStr+"_notEquals")) {
								criteria.add(Restrictions.or(Restrictions.isNull(auxStr), Restrictions.ne(auxStr, Enum.valueOf(fieldClass, filters.get(auxStr).toString()))));
							} else {
								criteria.add(Restrictions.eq(auxStr, Enum.valueOf(fieldClass, filters.get(auxStr).toString())));
							}
						} else if (fieldClass != null && fieldClass.equals(Date.class)) {
							boolean includeNull = false;
							if (auxSet.contains(auxStr+"_isNull")) {
								includeNull = true;
							}
							String[] aux = filters.get(auxStr).toString().split("#");
							if (!aux[0].equals("s")){
								if (includeNull) {
									criteria.add(Restrictions.or(Restrictions.ge(auxStr, new Date(Long.parseLong(aux[0]))), Restrictions.isNull(auxStr)));
								} else {
									criteria.add(Restrictions.ge(auxStr, new Date(Long.parseLong(aux[0]))));
								}
							} 
							if (!aux[1].equals("e")) {
								if (includeNull) {
									criteria.add(Restrictions.or(Restrictions.le(auxStr, new Date(Long.parseLong(aux[1]))), Restrictions.isNull(auxStr)));
								} else {
									criteria.add(Restrictions.le(auxStr, new Date(Long.parseLong(aux[1]))));
								}
								
							}
						} else if (fieldClass != null && fieldClass.equals(Integer.class)) {
							try {
								criteria.add(Restrictions.eq(auxStr, Integer.parseInt(filters.get(auxStr).toString())));
							} catch (NumberFormatException e) {
								e.printStackTrace();
							}
						} else if (fieldClass != null && fieldClass.equals(Long.class)) {
							criteria.add(Restrictions.eq(auxStr, Long.parseLong(filters.get(auxStr).toString())));
						} else {
							criteria.add(Restrictions.eq(auxStr, filters.get(auxStr)));
						}
					}
				}
			}
		}

		if (visibleSecurityLevels != null && visibleSecurityLevels.size() > 0) {
				criteria.add(Restrictions.in("securityLevel", visibleSecurityLevels));
		}

		if (listVisibleRoles != null && listVisibleRoles.size() > 0) {
			if (classz.equals(IRole.class)) {
				List<Long> listCods = new ArrayList<Long>();
				for (IRole aux : listVisibleRoles) {
					listCods.add(aux.getId());
				}
				criteria.add(Restrictions.in(FieldName.ID.getValue(), listCods));
			} else {
				criteria.add(Restrictions.in(FieldName.ROLE.getValue(), listVisibleRoles));
			}
		}

		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		return criteria.list();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" }) 
	@Override
	public Integer loadRowCount(Map<String, Object> filtersOriginal, Class classz,List<ISecurityLevel> visibleSecurityLevels, List<IRole> listVisibleRoles) {
		try{
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(classz);
		Map<String, Object> filters = new HashMap<String, Object>();
		if (filtersOriginal != null) {
			filters.putAll(filtersOriginal);
			filters.put(FieldName.DELETED.getValue(), false);
			filters.put(FieldName.ENABLED.getValue(), true);
		}
		Map<String, Object> auxAliasFilter = new HashMap<String, Object>();
		
		if (filters != null && filters.size() > 0) {
			Set<String> auxSet = filters.keySet();
			int auxCount = 0;
			for (String auxStr : auxSet) {
				//workaround to show terminals offline without last communication date
				if (!auxStr.contains("isNull") && !auxStr.contains("notEqual")) {
					List<String> auxAlias = Arrays.asList(auxStr.split("\\."));
					if (auxAlias != null && auxAlias.size() > 1) {
						Iterator<String> iterator = auxAlias.iterator();
						String prevAlias = null;
						do {
							String field = iterator.next();
							if (iterator.hasNext()) {
								if (prevAlias == null) {
									if (!auxAliasFilter.keySet().contains(field)) {
										criteria.createAlias(field, field);
										auxAliasFilter.put(field, field);
									}
								} else {
									if (!auxAliasFilter.keySet().contains(field)) {
										criteria.createAlias(prevAlias + "." + field, field);
										auxAliasFilter.put(field, prevAlias + "." + field);
									} else {
										if (!auxAliasFilter.get(field).equals(prevAlias + "." + field)) {
											criteria.createAlias(prevAlias + "." + field, field + auxCount);
											auxAliasFilter.put(field + auxCount, prevAlias + "." + field);

											field = field + auxCount;
											auxCount++;
										}
									}
								}
								prevAlias = field;
							} else {
								Class fieldClass = null;
								try {
									for (String aux : auxAlias) {
										String getMethod = "get" + aux.substring(0, 1).toUpperCase() + aux.substring(1);
										if (fieldClass == null) {
											fieldClass = classz.getMethod(getMethod).getReturnType();
										} else {
											fieldClass = fieldClass.getMethod(getMethod).getReturnType();
										}
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
								
								if (fieldClass != null && fieldClass.isEnum()) {
									if (auxSet.contains(auxStr+"_notEquals")) {
										criteria.add(Restrictions.or(Restrictions.isNull(prevAlias + "." + field), Restrictions.ne(prevAlias + "." + field, Enum.valueOf(fieldClass, filters.get(auxStr).toString()))));
									} else {
										criteria.add(Restrictions.eq(prevAlias + "." + field, Enum.valueOf(fieldClass, filters.get(auxStr).toString())));
									}
								} else {
									criteria.add(Restrictions.like(prevAlias + "." + field, "%" + filters.get(auxStr) + "%"));
								}
							}
						} while (iterator.hasNext());
					} else {
						String getMethod = "get" + auxStr.substring(0, 1).toUpperCase() + auxStr.substring(1);
						Class fieldClass = null;
						try {
							fieldClass = classz.getMethod(getMethod).getReturnType();
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						if (fieldClass != null && fieldClass.equals(String.class)) {
							if (auxStr.equals("serialNumber")){
								criteria.add(Restrictions.sqlRestriction("REVERSE({alias}.num_serie) like REVERSE(?)", "%" + filters.get(auxStr), StringType.INSTANCE));
							} else if (filters.get(auxStr).toString().length() == 0) {
								criteria.add(Restrictions.eq(auxStr, filters.get(auxStr)));
							} else {						
								criteria.add(Restrictions.like(auxStr, "%" + filters.get(auxStr) + "%"));
							}
						} else if (fieldClass != null && fieldClass.isEnum()) {
							if (auxSet.contains(auxStr+"_notEquals")) {
								criteria.add(Restrictions.or(Restrictions.isNull(auxStr), Restrictions.ne(auxStr, Enum.valueOf(fieldClass, filters.get(auxStr).toString()))));
							} else {
								criteria.add(Restrictions.eq(auxStr, Enum.valueOf(fieldClass, filters.get(auxStr).toString())));
							}
						} else if (fieldClass != null && fieldClass.equals(Date.class)) {
							boolean includeNull = false;
							if (auxSet.contains(auxStr+"_isNull")) {
								includeNull = true;
							}
							String[] aux = filters.get(auxStr).toString().split("#");
							if (!aux[0].equals("s")){
								if (includeNull) {
									criteria.add(Restrictions.or(Restrictions.ge(auxStr, new Date(Long.parseLong(aux[0]))), Restrictions.isNull(auxStr)));
								} else {
									criteria.add(Restrictions.ge(auxStr, new Date(Long.parseLong(aux[0]))));
								}
							} 
							if (!aux[1].equals("e")) {
								if (includeNull) {
									criteria.add(Restrictions.or(Restrictions.le(auxStr, new Date(Long.parseLong(aux[1]))), Restrictions.isNull(auxStr)));
								} else {
									criteria.add(Restrictions.le(auxStr, new Date(Long.parseLong(aux[1]))));
								}
								
							}
						} else if (fieldClass != null && fieldClass.equals(Integer.class)) {
							try {
								criteria.add(Restrictions.eq(auxStr, Integer.parseInt(filters.get(auxStr).toString())));
							} catch (NumberFormatException e) {
								// do nothing
							}
						} else if (fieldClass != null && fieldClass.equals(Long.class)) {
							criteria.add(Restrictions.eq(auxStr, Long.parseLong(filters.get(auxStr).toString())));
						} else {
							criteria.add(Restrictions.eq(auxStr, filters.get(auxStr)));
						}
					}
				}
			}
		}

		if (visibleSecurityLevels != null && visibleSecurityLevels.size() > 0) {
				criteria.add(Restrictions.in(FieldName.SECURITY_LEVEL.getValue(), visibleSecurityLevels));
		}

		if (listVisibleRoles != null && listVisibleRoles.size() > 0) {
			if (classz.equals(IRole.class)) {
				List<Long> listCods = new ArrayList<Long>();
				for (IRole aux : listVisibleRoles) {
					listCods.add(aux.getId());
				}
				criteria.add(Restrictions.in(FieldName.ID.getValue(), listCods));
			} else {
				criteria.add(Restrictions.in(FieldName.ROLE.getValue(), listVisibleRoles));
			}
		}

		criteria.setProjection(Projections.rowCount());
		return ((Long) criteria.uniqueResult()).intValue();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void reattach(Object entity) {
		getSessionFactory().getCurrentSession().refresh(entity);
	}

	@Override
	public void detach(Object entity) {
		getSessionFactory().getCurrentSession().evict(entity);
	}
	
	@Override
	public void initializeLazyList(Object list) {
		Hibernate.initialize(list);		
	}
}
