package com.escribo.common.foundation.view;

import javax.faces.context.FacesContext;

import com.escribo.common.foundation.model.GenericLazyDataModel;
import com.escribo.common.foundation.model.IModel;


public abstract class AbstractController<T extends IModel> implements ControllerSuport<T>{

	private T managedObject;
	
	private T selectedObject;
	
	private GenericLazyDataModel<T> objectDataModel;
	
	private Boolean viewOnly = false;
	
	public void saveOrUpdateObject(T object){
		
	}
	
	public void deleteObject(T Object){
		
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanName) {
		return (T) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, beanName);
	}

	public T getManagedObject() {
		return managedObject;
	}

	public void setManagedObject(T managedObject) {
		this.managedObject = managedObject;
	}

	public T getSelectedObject() {
		return selectedObject;
	}

	public void setSelectedObject(T selectedObject) {
		this.selectedObject = selectedObject;
	}

	public GenericLazyDataModel<T> getObjectDataModel() {
		return objectDataModel;
	}

	public void setObjectDataModel(GenericLazyDataModel<T> objectDataModel) {
		this.objectDataModel = objectDataModel;
	}
	
	public Boolean getViewOnly() {
		return viewOnly;
	}

	public void setViewOnly(Boolean viewOnly) {
		this.viewOnly = viewOnly;
	}


}
