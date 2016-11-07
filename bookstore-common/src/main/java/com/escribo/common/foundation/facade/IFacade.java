package com.escribo.common.foundation.facade;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import com.escribo.common.foundation.model.IModel;


public abstract interface IFacade {

	@SuppressWarnings("rawtypes")
	public List<IModel> loadList(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Class classz);

	@SuppressWarnings("rawtypes")
	public Integer loadRowCount(Map<String, Object> filters, Class classz);

}
