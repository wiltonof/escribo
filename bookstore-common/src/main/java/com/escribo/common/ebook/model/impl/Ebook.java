package com.escribo.common.ebook.model.impl;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.escribo.common.ebook.model.ICollection;
import com.escribo.common.ebook.model.IEbook;
import com.escribo.common.foundation.model.impl.AbstractModel;
import com.escribo.common.security.model.impl.Role;


@SuppressWarnings("deprecation")
@Entity
@Table(name = "ebooks", uniqueConstraints = { @UniqueConstraint(columnNames = { "id" }) })
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class Ebook extends AbstractModel implements IEbook {
	private static final long serialVersionUID = 7902464329704092378L;

	private String title;
	private String autor;
	private String schoolLevel;
	private Integer year;
	private String edition;
	private ICollection collection;
	
	@Column(name = "title", length = 250)
	@Override
	public String getTitle() {
		return title;
	}
	
	@Override
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(name = "autor", length = 250)
	@Override
	public String getAutor() {
		return autor;
	}
	
	@Override
	public void setAutor(String autor) {
		this.autor = autor;
	}
	
	@Column(name = "school_level", length = 250)
	@Override
	public String getSchoolLevel() {
		return schoolLevel;
	}
	
	@Override
	public void setSchoolLevel(String schoolLevel) {
		this.schoolLevel = schoolLevel;
	}
	
	@Column(name = "year")
	@Override
	public Integer getYear() {
		return year;
	}
	
	@Override
	public void setYear(Integer year) {
		this.year = year;
	}
	
	@Column(name = "edition", length = 250)
	@Override
	public String getEdition() {
		return edition;
	}
	
	@Override
	public void setEdition(String edition) {
		this.edition = edition;
	
	}
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.DETACH, CascadeType.REFRESH }, targetEntity = Role.class)
	@JoinColumn(name = "collection_id", nullable = false)
	@Override
	public ICollection getCollection() {
		return collection;
	}
	
	@Override
	public void setCollection(ICollection collection) {
		this.collection = collection;
	}
	
	
}
