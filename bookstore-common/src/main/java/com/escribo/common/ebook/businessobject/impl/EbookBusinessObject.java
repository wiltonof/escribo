package com.escribo.common.ebook.businessobject.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;

import com.escribo.common.ebook.businessobject.IEbookBusinessObject;
import com.escribo.common.ebook.dataprovider.IEbookDataprovider;
import com.escribo.common.ebook.model.IEbook;
import com.escribo.common.foundation.businessobject.impl.BusinessObjectImpl;
import com.escribo.common.security.facade.ISecurityFacade;

@Component
public class EbookBusinessObject extends BusinessObjectImpl<IEbook> implements IEbookBusinessObject {

	@Autowired
	private IEbookDataprovider ebookDataprovider; 
	
	@Autowired
	private ISecurityFacade securityFacade;
	
	@Secured(value="CRIAR_LIVRO")
	@Override
	public IEbook createEbook(IEbook object) {
		//TODO validar entradas 
		//TODO ajustar camos blob
		
		securityFacade.saveUserTransaction("CRIAR_LIVRO", object, object.getCollection());
		return (IEbook) ebookDataprovider.saveOrUpdate(object);
	}

	@Secured(value="ATUALIZAR_LIVRO")
	@Override
	public IEbook updateEbook(IEbook object) {
		// TODO verificar se os campos a ser modificados são permitidos
		securityFacade.saveUserTransaction("ATUALIZAR_LIVRO", object, object.getCollection());
		return (IEbook) ebookDataprovider.saveOrUpdate(object);
	}

	@Secured(value="EXCLUIR_LIVRO")
	@Override
	public void deleteEbook(IEbook object) {
		
		securityFacade.saveUserTransaction("EXCLUIR_LIVRO", object, object.getCollection());
		ebookDataprovider.delete(object);		
	}

}
