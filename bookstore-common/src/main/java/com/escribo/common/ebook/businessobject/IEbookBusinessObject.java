package com.escribo.common.ebook.businessobject;

import com.escribo.common.ebook.model.IEbook;
import com.escribo.common.foundation.businessobject.IBusinessObject;

public interface IEbookBusinessObject extends IBusinessObject<IEbook> {

	public IEbook createEbook(IEbook object);

	public IEbook updateEbook(IEbook object);

	public void deleteEbook(IEbook object);
	
}
