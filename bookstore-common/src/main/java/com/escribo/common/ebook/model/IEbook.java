package com.escribo.common.ebook.model;

import java.sql.Blob;
import java.util.List;

import com.escribo.common.foundation.model.IModel;

public interface IEbook extends IModel {
	public String getTitle();
	public void setTitle(String title);
	public String getAutor();
	public void setAutor(String autor);
	public String getSchoolLevel();
	public void setSchoolLevel(String schoolLevel);
	public Integer getYear();
	public void setYear(Integer year);
	public String getEdition();
	public void setEdition(String edition);
	public ICollection getCollection();
	public void setCollection(ICollection collection);
	public Blob getEbook();
	public void setEbook(Blob ebook);
	public Blob getThumb();
	public void setThumb(Blob thumb);
	public List<IDiscipline> getDisciplines();
	public void setDisciplines(List<IDiscipline> disciplines);
}
