package com.escribo.common.foundation.model;
import java.io.Serializable;
import java.util.List;

import javax.faces.model.DataModelEvent;
import javax.faces.model.DataModelListener;
import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

public class GenericListDataModel<T> extends ListDataModel<T> implements SelectableDataModel<T>, Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7030007801693946981L;
	// FIELDS
	private int _rowIndex = -1;
	private List<T> _data;

	// CONSTRUCTORS
	public GenericListDataModel() {
		super();
	}

	public GenericListDataModel(List<T> list) {
		if (list == null) {
			throw new NullPointerException("list");
		}
		setWrappedData(list);
	}

	// METHODS

	public int size() {
		if (this._data != null) {
			return _data.size();
		} else {
			return 0;
		}
	}

	@Override
	public T getRowData() {
		if (_data == null) {
			return null;
		}
		if (!isRowAvailable()) {
			throw new IllegalArgumentException("row is unavailable");
		}
		return _data.get(_rowIndex);
	}

	@Override
	public T getRowData(String rowKey) {
		@SuppressWarnings("unchecked")
		List<T> models = (List<T>) getWrappedData();

		if (models != null) {
			for (T model : models) {
				if (((IModel) model).getId() == Integer.parseInt(rowKey))
					return model;
			}
		}

		return null;
	}

	@Override
	public Object getRowKey(T model) {
		return ((IModel) model).getId();
	}

	@Override
	public int getRowCount() {
		if (_data == null) {
			return -1;
		}
		return _data.size();
	}

	@Override
	public int getRowIndex() {
		return _rowIndex;
	}

	@Override
	public Object getWrappedData() {
		return _data;
	}

	@Override
	public boolean isRowAvailable() {
		return _data != null && _rowIndex >= 0 && _rowIndex < _data.size();
	}

	@Override
	public void setRowIndex(int rowIndex) {
		if (rowIndex < -1) {
			throw new IllegalArgumentException("illegal rowIndex " + rowIndex);
		}
		int oldRowIndex = _rowIndex;
		_rowIndex = rowIndex;
		if (_data != null && oldRowIndex != _rowIndex) {
			T data = isRowAvailable() ? getRowData() : null;
			DataModelEvent event = new DataModelEvent(this, _rowIndex, data);
			DataModelListener[] listeners = getDataModelListeners();
			for (int i = 0; i < listeners.length; i++) {
				listeners[i].rowSelected(event);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setWrappedData(Object data) {
		if (data == null) {
			setRowIndex(-1);
			_data = null;
		} else {
			_data = (List<T>) data;
			_rowIndex = -1;
			setRowIndex(0);
		}
	}
}
