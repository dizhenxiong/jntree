package com.lenovo.vctl.dal.cache.memcached.route.strategy;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.lenovo.vctl.dal.cache.config.model.KeyPatternItem;
import com.lenovo.vctl.dal.cache.exception.StrategyException;
import com.lenovo.vctl.dal.cache.memcached.config.helper.CacheConfigHelper;
import com.lenovo.vctl.dal.cache.route.strategy.IStrategy;


public class DefaultStrategy implements IStrategy {
    private static Logger logger = Logger.getLogger(DefaultStrategy.class);

    public Object playStrategy(String regionName, Object key) throws StrategyException {
        if (StringUtils.isEmpty(regionName)) {
            throw new StrategyException("region name must have a value");
        }

        if (key == null) {
            throw new StrategyException("key must have a value");
        }
        
        Map<String, KeyPatternItem> keyPatternMap = CacheConfigHelper.getKeyKeyPatternItems(regionName);
        
        if (MapUtils.isEmpty(keyPatternMap)) { //如果找不到配置，就用缺省的
            logger.warn("don't find KeyPatternItems of the " + regionName + " , begin use default set!");            
            keyPatternMap = CacheConfigHelper.getDefaultKeyKeyPatternItems();
        }

        if (MapUtils.isNotEmpty(keyPatternMap)) {
            KeyPatternItem keyPatternItem = findPatternItem(keyPatternMap, key);
            return keyPatternItem != null ? keyPatternItem.getDatasource() : null;
        }
        return null;
    }

    /**
     * 想找到想用的patternItem
     * 
     * @param patternMap
     * @param account
     * @return
     */
    private KeyPatternItem findPatternItem(Map<String, KeyPatternItem> patternMap, Object key) {
        KeyPatternItem patternItem = null;
        Collection<KeyPatternItem> patternItems = patternMap.values();
        for (Iterator iterator = patternItems.iterator(); iterator.hasNext();) {
            KeyPatternItem item = (KeyPatternItem) iterator.next();
            if (item != null) {
                String value = item.getValue();
                if (StringUtils.isNotEmpty(value)) {
                    Pattern pattern = Pattern.compile(value);
                    if (pattern.matcher(ObjectUtils.toString(key)).find()) { // 找到了就退出
                        patternItem = item;
                        break;
                    }
                }
            }
        }
        return patternItem != null ? patternItem : patternItems.iterator().next();
    }
}
