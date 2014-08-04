/**
 * 
 */
package com.lenovo.vctl.dal.dao.config.model.dao;

import java.io.Serializable;

/**
 * @author allenshen
 * 
 * date: Nov 17, 2008 11:03:44 AM
 */
public class PatternItem implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -4930138449702188483L;
    private String value;
    private String group;

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value
     *            the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return the group
     */
    public String getGroup() {
        return group;
    }

    /**
     * @param group
     *            the group to set
     */
    public void setGroup(String group) {
        this.group = group;
    }

    /**
     * 
     * @return
     * @author
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("PatternItem[");
        buffer.append("group = ").append(group);
        buffer.append("\n value = ").append(value);
        buffer.append("]");
        return buffer.toString();
    }

}
