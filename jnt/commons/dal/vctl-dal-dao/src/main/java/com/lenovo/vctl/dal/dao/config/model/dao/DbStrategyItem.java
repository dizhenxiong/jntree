/**
 * 
 */
package com.lenovo.vctl.dal.dao.config.model.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author allenshen
 * 
 * date: Nov 17, 2008 10:58:40 AM
 */
public class DbStrategyItem implements Serializable {
    private static Logger logger = Logger.getLogger(DbStrategyItem.class);

    /**
     * 
     */
    private static final long serialVersionUID = -6388822807404747566L;

    private String name;
    private String clasz;
    private String objectName;
    private Map< String, PatternItem > patternItemMap = new HashMap< String, PatternItem >();
    private List< PatternItem > patternItemList = new ArrayList<PatternItem>();
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the clasz
     */
    public String getClasz() {
        return clasz;
    }

    /**
     * @param clasz
     *            the clasz to set
     */
    public void setClasz(String clasz) {
        this.clasz = clasz;
    }

    public void addPatternItem(PatternItem patternItem) {
        if (patternItem != null && StringUtils.isNotEmpty(patternItem.getValue())) {
            if (!patternItemMap.containsKey(patternItem.getValue())) {
                patternItemMap.put(patternItem.getValue(), patternItem);
                patternItemList.add(patternItem);
            } else {
                logger.error("same <" + patternItem.getValue() + "> value PatternItem have exist!");
            }
        } else {
            logger.error(" PatternItem must has value and value isn't empty");
        }
    }

    /**
     * @return the objectName
     */
    public String getObjectName() {
        return objectName;
    }

    /**
     * @param objectName
     *            the objectName to set
     */
    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    /**
     * 
     * @return
     * @author
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("DbStrategyItem[");
        buffer.append("clasz = ").append(clasz);
        buffer.append("\n name = ").append(name);
        buffer.append("\n objectName = ").append(objectName);
        buffer.append("\n patternItemMap = ").append(patternItemMap);
        buffer.append("]");
        return buffer.toString();
    }

    public Map< String, PatternItem > getPatternItemMap() {
        return patternItemMap;
    }

    public List<PatternItem> getPatternItemList() {
        return patternItemList;
    }

}
