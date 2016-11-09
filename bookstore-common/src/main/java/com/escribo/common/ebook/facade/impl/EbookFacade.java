package com.escribo.common.ebook.facade.impl;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.escribo.common.ebook.businessobject.IEbookBusinessObject;
import com.escribo.common.ebook.facade.IEbookFacade;
import com.escribo.common.ebook.model.IEbook;
import com.escribo.common.foundation.model.IModel;

@Component
public class EbookFacade implements IEbookFacade {
	
	@Autowired
	private IEbookBusinessObject  ebookBusinessObject;

	@Override
	public List<IModel> loadList(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Class classz) {
		// TODO Auto-generated method stub
		return ebookBusinessObject.loadList(first, pageSize, sortField, sortOrder, filters, classz);
	}

	@Override
	public Integer loadRowCount(Map<String, Object> filters, Class classz) {
		// TODO Auto-generated method stub
		return ebookBusinessObject.loadRowCount(filters, classz);
	}

	@Override
	public IEbook createEbook(IEbook object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IEbook updateEbook(IEbook object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteEbook(IEbook object) {
		// TODO Auto-generated method stub
		
	}


}
