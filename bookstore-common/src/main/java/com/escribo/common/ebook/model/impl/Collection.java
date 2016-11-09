package com.escribo.common.ebook.model.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.escribo.common.ebook.model.ICollection;
import com.escribo.common.foundation.model.impl.AbstractModel;


@SuppressWarnings("deprecation")
@Entity
@Table(name = "collections", uniqueConstraints = { @UniqueConstraint(columnNames = { "id"}) })
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class Collection extends AbstractModel implements ICollection {
	private static final long serialVersionUID = 6374825449389219086L;

	private String name;
	private String description;
	private Integer year;
	
	@Column(name = "name", length = 250)
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "description", length = 250)
	@Override
	public String getDescription() {
		return description;
	}
	
	@Column(name = "description", length = 250)
	@Override
	public void setDescription(String description) {
		this.description = description;
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
}
