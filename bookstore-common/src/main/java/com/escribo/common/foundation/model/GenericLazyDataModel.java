package com.escribo.common.foundation.model;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.escribo.common.foundation.facade.IFacade;


public class GenericLazyDataModel<T extends IModel> extends LazyDataModel<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4982120607825130111L;
	private String facade;
	private final Class<? extends T> type;
	private List<T> list;
	private Boolean reload;
	private Long idParent = null;

	public GenericLazyDataModel(Class<? extends T> type, String facade) {
		super();
		this.type = type;
		this.facade = facade;
		this.reload = true;
	}

	public GenericLazyDataModel(Class<? extends T> type, String facade, Long id) {
		super();
		this.type = type;
		this.facade = facade;
		this.reload = true;
		this.setIdParent(id);
	}
	
	@Override
	public Object getRowKey(T object) {
		return ((IModel) object).getId();
	}
	
	@Override
	public void setRowIndex(int rowIndex) {
		if (rowIndex == -1 || getPageSize() == 0) {
            super.setRowIndex(-1);
        }
        else {
        	super.setRowIndex(rowIndex);
        }
    }

	@SuppressWarnings("unchecked")
	@Override
	public T getRowData(String rowKey) {
		List<T> aux = (List<T>) getWrappedData();
		if (aux != null) {
			for (T model : aux) {
				if (rowKey != null && !rowKey.equals("null") && ((IModel) model).getId() == Integer.parseInt(rowKey))
					return model;
			}
		}
		return null;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
		
		if (this.list == null || this.reload == true) {
			List<T> aux = (List<T>) getBean(facade).loadList(first, pageSize, sortField, sortOrder, filters, type);
			if (aux != null) {
				this.list = aux;
				setRowCount(getBean(facade).loadRowCount(filters, type));
			} else {
				setRowCount(0);
			}
			this.reload = true;
			return aux;
		} else {
			this.reload = true;
			return this.list;
		}
	}


	public Class<? extends T> getMyType() {
		return this.type;
	}

	public static IFacade getBean(String beanName) {
		return (IFacade) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, beanName);
	}

	public Boolean getReload() {
		return reload;
	}

	public void setReload(Boolean reload) {
		this.reload = reload;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public Long getIdParent() {
		return idParent;
	}

	public void setIdParent(Long idParent) {
		this.idParent = idParent;
	}
}