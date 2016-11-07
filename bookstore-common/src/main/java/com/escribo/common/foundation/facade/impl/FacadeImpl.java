package com.escribo.common.foundation.facade.impl;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import com.escribo.common.foundation.facade.IFacade;
import com.escribo.common.foundation.model.IModel;

@SuppressWarnings("rawtypes")
public abstract class FacadeImpl implements IFacade {

	@Override
	public List<IModel> loadList(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Class classz) {
		return null;
	}

	@Override
	public Integer loadRowCount(Map<String, Object> filters,  Class classz) {
		
		return null;
	}

}
