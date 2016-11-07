package com.escribo.common.security.dataprovider.impl;


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.escribo.common.foundation.dataprovider.impl.DataProviderImpl;
import com.escribo.common.foundation.model.FieldName;
import com.escribo.common.foundation.model.IModel;
import com.escribo.common.security.dataprovider.ISecurityDataProvider;
import com.escribo.common.security.model.IRole;
import com.escribo.common.security.model.IUser;
import com.escribo.common.security.model.IUserAccessData;
import com.escribo.common.security.model.impl.Role;
import com.escribo.common.security.model.impl.User;
import com.escribo.common.security.model.impl.UserAccessData;
import com.escribo.common.util.AuxiliaryFunctions;

@Component
public class SecurityDataProvider extends DataProviderImpl implements ISecurityDataProvider {


	@SuppressWarnings("unchecked")
	public IUser loadUserByUserName(String userName) {
		List<IModel>users;
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(User.class);

		criteria.add(
				Restrictions.and(
						Restrictions.eq(FieldName.USER_NAME.getValue(), userName),
						Restrictions.eq(FieldName.DELETED.getValue(), false),
						Restrictions.eq(FieldName.ENABLED.getValue(), true)
				)
		);		
		criteria.addOrder(Order.desc(FieldName.CREATED_AT.getValue()));
		criteria.setMaxResults(2);		
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		users = criteria.list();
		if(AuxiliaryFunctions.isNullOrEmpty(users)){
			return (IUser)users.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public IUserAccessData loadPrevUserAccessData(Long id) {
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(UserAccessData.class);
		criteria.add(
				Restrictions.and(
						Restrictions.eq(FieldName.USER_ID.getValue(), id),
						Restrictions.eq(FieldName.DELETED.getValue(), false),
						Restrictions.eq(FieldName.ENABLED.getValue(), true)
				)
		);		
		criteria.addOrder(Order.desc(FieldName.CREATED_AT.getValue()));
		criteria.setMaxResults(2);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<Object> result = criteria.list();
		if (result != null) {
			if (result.size() == 2) {
				return (IUserAccessData) result.get(1);
			} else {
				return (IUserAccessData) result.get(0);
			}

		} else {
			return null;
		}
	}

	@Override
	public Integer loadUserAccessNumber(Long id) {
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(UserAccessData.class);
		criteria.add(
				Restrictions.and(
						Restrictions.eq(FieldName.USER_ID.getValue(), id),
						Restrictions.eq(FieldName.DELETED.getValue(), false),
						Restrictions.eq(FieldName.ENABLED.getValue(), true)
				)
		);			
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setProjection(Projections.rowCount());
		return ((Long) criteria.uniqueResult()).intValue();
	}




	@SuppressWarnings("unchecked")
	@Override
	public List<IRole> loadListRoles(List<Integer> listVisibleRoles) {
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Role.class);
		
		criteria.add(
				Restrictions.and(
						Restrictions.in(FieldName.ID.getValue(), listVisibleRoles),
						Restrictions.eq(FieldName.DELETED.getValue(), false),
						Restrictions.eq(FieldName.ENABLED.getValue(), true)
				)
		);	
		criteria.addOrder(Order.asc(FieldName.DESCRIPTION.getValue()));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	



	@SuppressWarnings("unchecked")
	@Override
	public IRole findRoleByDescription(String description) {
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Role.class);
		criteria.add(
				Restrictions.and(
						Restrictions.eq(FieldName.DESCRIPTION.getValue(), description),
						Restrictions.eq(FieldName.DELETED.getValue(), false),
						Restrictions.eq(FieldName.ENABLED.getValue(), true)
				)
		);	
		List<IRole> list = criteria.list();
		if(list != null && !list.isEmpty()){
			return list.get(0);
		}
		return null;
	}

}
