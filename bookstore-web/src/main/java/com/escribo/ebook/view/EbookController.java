package com.escribo.ebook.view;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.springframework.beans.factory.annotation.Configurable;

import com.escribo.common.ebook.model.IEbook;
import com.escribo.common.foundation.view.AbstractController;

@ManagedBean(name="ebookMB")
@ViewScoped
@Configurable
public class EbookController extends AbstractController<IEbook> {

}
