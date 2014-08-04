package com.lenovo.vctl.dal.dao.model;

import org.hibernate.type.Type;

public class ScalarInfo {

	private String columnName;
	private Type   columnType;
	
	public ScalarInfo(String name,Type type){
		this.columnName = name;
		this.columnType = type;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public Type getColumnType() {
		return columnType;
	}
	public void setColumnType(Type columnType) {
		this.columnType = columnType;
	}
	
}
