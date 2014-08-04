package com.lenovo.vctl.dal.cache.config.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/*
 * 
 */
public class RegionItem implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -2427500931734139981L;
    private static Logger logger = Logger.getLogger(RegionItem.class);
    private String name;
    private String listenerClass;
    private String strategyClass;
    private boolean hasClassInfo = true;

    private int limitLen = 300;
    private int initLen = 100;
    
    private Map<String, KeyPatternItem> keyPatternMap = new HashMap<String, KeyPatternItem>();
    private boolean localCache;

    private boolean remoteCache = true;
    
    
    
    public boolean isRemoteCache() {
		return remoteCache;
	}

	public void setRemoteCache(boolean remoteCache) {
		this.remoteCache = remoteCache;
	}

	public boolean isLocalCache() {
        return localCache;
    }

    public void setLocalCache(boolean localCache) {
        this.localCache = localCache;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getListenerClass() {
        return listenerClass;
    }

    public void setListenerClass(String listenerClass) {
        this.listenerClass = listenerClass;
    }

    public Map<String, KeyPatternItem> getKeyPatternMap() {
        return keyPatternMap;
    }

    public void addKeyPatternItem(KeyPatternItem item) {
        if (item != null && StringUtils.isNotEmpty(item.getValue())) {
            if (!keyPatternMap.containsKey(item.getValue())) {
                keyPatternMap.put(item.getValue(), item);
            } else {
                logger.error("region: " + StringUtils.defaultIfEmpty(name, "") + " same patternitem <"
                        + item.getValue() + "> exist!");
            }
        } else {
            logger.error("KeyPatternItem object is null or value is null, please set in memcached_client.xml.");
        }
    }
    
    public void resetKeyPatternItems(List<KeyPatternItem> items) {
    	if (CollectionUtils.isEmpty(items)) return;
    	
    	Map<String, KeyPatternItem> newKeyPs = new HashMap<String, KeyPatternItem>();
    	for(KeyPatternItem item : items) {
    		if (item != null && StringUtils.isNotEmpty(item.getValue())) {
                if (!newKeyPs.containsKey(item.getValue())) {
                	newKeyPs.put(item.getValue(), item);
                } else {
                    logger.error("region: " + StringUtils.defaultIfEmpty(name, "") + " same patternitem <"
                            + item.getValue() + "> exist!");
                }
            } else {
                logger.error("KeyPatternItem object is null or value is null, please set in memcached_client.xml.");
            }
    	}
    	
    	//replace
    	if (MapUtils.isNotEmpty(newKeyPs)) {
    		this.keyPatternMap = newKeyPs;
    	}
    }

    public String getStrategyClass() {
        return strategyClass;
    }

    public void setStrategyClass(String strategyClass) {
        this.strategyClass = strategyClass;
    }

    public boolean isHasClassInfo() {
        return hasClassInfo;
    }

    public void setHasClassInfo(boolean hasClassInfo) {
        this.hasClassInfo = hasClassInfo;
    }

    public int getLimitLen() {
        return limitLen;
    }

    public void setLimitLen(int limitLen) {
        this.limitLen = limitLen;
    }

    public int getInitLen() {
        return initLen;
    }

    public void setInitLen(int initLen) {
        this.initLen = initLen;
    }

    /**
         * 
         * @return 
         * @author 
         */
        public String toString() {
            StringBuffer buffer = new StringBuffer();
            buffer.append("RegionItem[");
            buffer.append("hasClassInfo = ").append(hasClassInfo);
            buffer.append(",\n initLen = ").append(initLen);
            buffer.append(",\n keyPatternMap = ").append(keyPatternMap);
            buffer.append(",\n limitLen = ").append(limitLen);
            buffer.append(",\n listenerClass = ").append(listenerClass);
            buffer.append(",\n localCache = ").append(localCache);
            buffer.append(",\n name = ").append(name);
            buffer.append(",\n strategyClass = ").append(strategyClass);
            buffer.append("]");
            return buffer.toString();
        }

}
