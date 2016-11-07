package com.escribo.common.security.model.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import com.escribo.common.foundation.model.impl.AbstractModel;
import com.escribo.common.security.model.IPermission;





@SuppressWarnings("deprecation")
@Entity
@Table(name = "permission", uniqueConstraints = { @UniqueConstraint(columnNames = { "id", "description" }) })
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class Permission extends AbstractModel implements IPermission {
	private static final long serialVersionUID = -2461426687229268655L;

	private String description;
	private String observation;

	@Column(name = "description", nullable = false, length = 40)
	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	@Transient
	public String getAuthority() {
		return this.description;
	}

	@Column(name = "obs", nullable = true, length = 200)
	@Override
	public String getObservation() {
		return this.observation;
	}

	@Override
	public void setObservation(String observation) {
		this.observation = observation;
	}

	@Override
	public int compareTo(IPermission o) {
		return this.description.compareToIgnoreCase(o.getDescription());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((observation == null) ? 0 : observation.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Permission other = (Permission) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (observation == null) {
			if (other.observation != null)
				return false;
		} else if (!observation.equals(other.observation))
			return false;
		return true;
	}

}
