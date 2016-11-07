package com.escribo.common.foundation.model;
public enum FieldName {
	CREATED_AT("createdAt"),
	UPDATED_AT("updatedAt"),
	ENABLED("enabled"),
	DELETED("deleted"),
	SYSTEM("system"),
	ID("id"),
	DESCRIPTION("description"),
	NAME("name"),
	USER_ID("userId"),
	USER_NAME("username"),
	ROLE("role"),
	SECURITY_LEVEL("securityLevel");

	
	private String value;
	
	private FieldName(String value) {
		this.setValue(value);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
