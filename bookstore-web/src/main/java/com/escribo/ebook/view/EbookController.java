package com.escribo.ebook.view;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.springframework.beans.factory.annotation.Configurable;

import com.escribo.common.ebook.model.IEbook;
import com.escribo.common.foundation.model.GenericLazyDataModel;
import com.escribo.common.foundation.view.AbstractController;

@ManagedBean(name="ebookMB")
@ViewScoped
@Configurable
public class EbookController extends AbstractController<IEbook> implements Serializable {
	private static final long serialVersionUID = -9167127188977226110L;

	public EbookController() {
		setObjectDataModel(new GenericLazyDataModel<IEbook>(IEbook.class, "ebookFacade"));
	}
	
}
