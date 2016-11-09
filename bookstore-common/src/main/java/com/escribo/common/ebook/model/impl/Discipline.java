package com.escribo.common.ebook.model.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.escribo.common.ebook.model.IDiscipline;
import com.escribo.common.foundation.model.impl.AbstractModel;

@SuppressWarnings("deprecation")
@Entity
@Table(name = "disciplines", uniqueConstraints = { @UniqueConstraint(columnNames = { "id" }) })
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class Discipline extends AbstractModel implements IDiscipline {
	private static final long serialVersionUID = 6554638283399347041L;
	
	private String name;
	private String description;
	
	@Column(name = "name", length = 250)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "description", length = 250)
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
