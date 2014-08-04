package com.lenovo.vctl.dal.dao.model;

import java.util.List;

public class SqlInfo {

	private String sql;
	private Object[] params;
	private List<ScalarInfo> scalarList;
	
    public SqlInfo(){
    	;
    }
    
    public SqlInfo(String SQL,Object[] paramArray,List<ScalarInfo> scaList){
    	sql = SQL;
    	params = paramArray;
    	scalarList = scaList;
    }
	
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public Object[] getParams() {
		return params;
	}
	public void setParams(Object[] params) {
		this.params = params;
	}
	public List<ScalarInfo> getScalarList() {
		return scalarList;
	}
	public void setScalarList(List<ScalarInfo> scalarList) {
		this.scalarList = scalarList;
	}
	
	public String getParamKey(){
		StringBuffer strBuf = new StringBuffer();
		if(null != params){
			for(Object obj: params){
				strBuf.append(obj).append("-");
			}
		}
		return strBuf.toString();
	}
	
}
