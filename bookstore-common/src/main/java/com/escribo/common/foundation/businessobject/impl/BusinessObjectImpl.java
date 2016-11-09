package com.escribo.common.foundation.businessobject.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.escribo.common.foundation.businessobject.IBusinessObject;
import com.escribo.common.foundation.model.IModel;
import com.escribo.common.foundation.model.ReportColumn;
import com.escribo.common.security.model.IUser;
import com.escribo.common.util.MsgFactory;
import com.escribo.common.util.Pair;

public abstract class BusinessObjectImpl<T extends IModel> implements IBusinessObject<T>  {

	
	@Autowired
	protected ServletContext servletContext;
	
	@Autowired
	protected MsgFactory message;
	
	@Value("${custom.logo_header_land_report}")
	private String logoReportLand;

	public IUser getAuthenticatedUser() {
		SecurityContext context = SecurityContextHolder.getContext();
		if (context == null)
			return null;

		Authentication authentication = context.getAuthentication();
		if (authentication == null)
			return null;

		return (IUser) authentication.getPrincipal();
	}
	
	

	public void generateReport(List<ReportColumn> columns, IModel filterObject, Map<String, Pair<Date, Date>> dateFilter, String reportExt, String reportTitle, String reportFileName, IBusinessObject businessObject) {

	}
	
	
	@Override
	public List<IModel> loadList(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Class classz) {
		return null;
	}

	@Override
	public Integer loadRowCount(Map<String, Object> filters, Class classz) {
		return null;
	}

	@Override
	public List<? extends IModel> loadList(IModel filter, Map<String, Pair<Date, Date>> dateFilter, String orderField,	String order) {
		return null;
	}
	
	@Secured(value=T.CREATE)
	public T create(T object){
		
		return object;
	}
	
	public T update(T object){
		
		return object;
	}
	
	public void delete(T object){
		
	}
	
	public T getById(Long id){
		T object = null;
		
		return object;
	}
}
