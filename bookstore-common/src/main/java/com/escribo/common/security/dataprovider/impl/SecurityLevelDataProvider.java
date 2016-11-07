package com.escribo.common.security.dataprovider.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.escribo.common.foundation.dataprovider.impl.DataProviderImpl;
import com.escribo.common.foundation.model.FieldName;
import com.escribo.common.foundation.model.IModel;
import com.escribo.common.security.dataprovider.ISecurityLevelDataProvider;
import com.escribo.common.security.model.ISecurityLevel;
import com.escribo.common.security.model.impl.SecurityLevel;

@Component
public class SecurityLevelDataProvider extends DataProviderImpl implements ISecurityLevelDataProvider {

	@SuppressWarnings("unchecked")
	@Override
	public List<ISecurityLevel> loadListAllSecurityLevels() {
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(SecurityLevel.class);
		criteria.add(
				Restrictions.and(
						Restrictions.eq(FieldName.DELETED.getValue(), false),
						Restrictions.eq(FieldName.ENABLED.getValue(), true)
				));
		criteria.addOrder(Order.asc("parentLevel"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}
	
	@Override
	public ISecurityLevel loadSecurityLevelByAbbreviation(String abbreviation) {
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(SecurityLevel.class);
		criteria.add(
				Restrictions.and(
						Restrictions.eq("abbreviation", abbreviation),
						Restrictions.eq(FieldName.DELETED.getValue(), false),
						Restrictions.eq(FieldName.ENABLED.getValue(), true)
				));
		criteria.addOrder(Order.desc(IModel.CREATE));
		criteria.setMaxResults(1);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		@SuppressWarnings("unchecked")
		List<ISecurityLevel> list = criteria.list();
		if(list != null && !list.isEmpty()){
			return list.get(0);
		}
		return null;
	}	
}
