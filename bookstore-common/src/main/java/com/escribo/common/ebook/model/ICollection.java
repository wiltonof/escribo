package com.escribo.common.ebook.model;

import com.escribo.common.foundation.model.IModel;

public interface ICollection extends IModel {
	public String getName();
	public void setName(String name);
	public String getDescription();
	public void setDescription(String description);
	public Integer getYear();
	public void setYear(Integer year);
}
