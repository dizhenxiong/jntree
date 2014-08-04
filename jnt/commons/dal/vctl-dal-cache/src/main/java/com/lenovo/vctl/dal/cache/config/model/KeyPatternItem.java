package com.lenovo.vctl.dal.cache.config.model;

import java.io.Serializable;
/**
 * 
 * 
 * 
 * @author allenshen
 * date: Dec 2, 2008 1:42:28 PM
 * Copyright 2008 Sohu.com Inc. All Rights Reserved.
 */
public class KeyPatternItem implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 369483380571049497L;
    private String value;
    private String datasource;
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }

    public String getDatasource() {
        return datasource;
    }
    public void setDatasource(String datasource) {
        this.datasource = datasource;
    }
    /**
         * 
         * @return 
         * @author 
         */
        public String toString() {
            StringBuffer buffer = new StringBuffer();
            buffer.append("KeyPatternItem[");
            buffer.append("datasource = ").append(datasource);
            buffer.append(" value = ").append(value);
            buffer.append("]");
            return buffer.toString();
        }
    
}
