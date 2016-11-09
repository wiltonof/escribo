package com.escribo.common.foundation.businessobject;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import com.escribo.common.foundation.model.IModel;
import com.escribo.common.util.Pair;


public interface IBusinessObject<T> {
	
	@SuppressWarnings("rawtypes")
	public List<IModel> loadList(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Class classz);

	@SuppressWarnings("rawtypes")
	public Integer loadRowCount(Map<String, Object> filters, Class classz);
	
	public List<? extends IModel> loadList(IModel filter, Map<String, Pair<Date, Date>> dateFilter, String orderField, String order);

	public T create(T object);
	
	public T update(T object);
	
	public void delete(T object);
	
	public T getById(Long id);
}
