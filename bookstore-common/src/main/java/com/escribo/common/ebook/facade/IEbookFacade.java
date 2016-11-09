package com.escribo.common.ebook.facade;

import com.escribo.common.ebook.model.IEbook;
import com.escribo.common.foundation.facade.IFacade;

public interface IEbookFacade extends IFacade {

	public IEbook createEbook(IEbook object);

	public IEbook updateEbook(IEbook object);

	public void deleteEbook(IEbook object);
	
}
