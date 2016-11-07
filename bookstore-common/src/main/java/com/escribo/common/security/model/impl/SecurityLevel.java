package com.escribo.common.security.model.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.escribo.common.foundation.model.impl.AbstractModel;
import com.escribo.common.security.model.ISecurityLevel;



@SuppressWarnings("deprecation")
@Entity
@Table(name = "security_level", uniqueConstraints = { @UniqueConstraint(columnNames = { "id", "abbreviation" }) })
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class SecurityLevel extends AbstractModel implements ISecurityLevel {
	private static final long serialVersionUID = -3399457861206974443L;

	private String description;
	private String abbreviation;
	private Long parentLevel;
	private Long parent;
	
	@Column(name = "description", length = 250)
	@Override
	public String getDescription() {
		return description;
	}
	
	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "parent_level", nullable = false)
	@Override
	public Long getParentLevel() {
		return parentLevel;
	}

	@Override
	public void setParentLevel(Long parentLevel) {
		this.parentLevel = parentLevel;
	}
	
	@Column(name = "abbreviation", nullable = false, length = 20, unique = true)
	@Override
	public String getAbbreviation() {
		return abbreviation;
	}
	
	@Override
	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}
	
	@Column(name = "parent_id", columnDefinition="bigint(20)")
	@Override
	public Long getParent() {
		return parent;
	}
	
	@Override
	public void setParent(Long parent) {
		this.parent = parent;
	}
	
}
