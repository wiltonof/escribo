package com.escribo.common.foundation.model;

import java.io.Serializable;

@SuppressWarnings("rawtypes")
public class ReportColumn implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -429617588080498997L;

	private String label;
	private String field;
	private Integer width;
	private Class type;
	private String order;
	private String pattern;
	
	public ReportColumn(String label, String field, Class type) {
		super();
		this.label = label;
		this.field = field;
		this.type = type;
		this.width = null;
		this.pattern = null;
	}

	public ReportColumn(String label, String field, Class type, Integer width) {
		super();
		this.label = label;
		this.field = field;
		this.type = type;
		this.width = width;
		this.pattern = null;
	}
	
	public ReportColumn(String label, String field, Class type, String pattern) {
		super();
		this.label = label;
		this.field = field;
		this.type = type;
		this.width = null;
		this.pattern = pattern;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Class getType() {
		return type;
	}

	public void setType(Class type) {
		this.type = type;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

}
