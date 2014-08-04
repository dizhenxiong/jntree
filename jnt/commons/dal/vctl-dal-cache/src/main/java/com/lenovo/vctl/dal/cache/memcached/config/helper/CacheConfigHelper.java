package com.lenovo.vctl.dal.cache.memcached.config.helper;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.lenovo.vctl.dal.cache.config.model.KeyPatternItem;
import com.lenovo.vctl.dal.cache.config.model.QueueItem;
import com.lenovo.vctl.dal.cache.config.model.RegionItem;
import com.lenovo.vctl.dal.cache.memcached.channel.source.MemcachedSource;
import com.lenovo.vctl.dal.cache.memcached.config.CacheConfig;


public final class CacheConfigHelper {
    private static Logger logger = Logger.getLogger(CacheConfigHelper.class);

    /**
     * 给出Region对应的策略表达式Map
     * 
     * @param regionName
     * @return
     */
    public static Map<String, KeyPatternItem> getKeyKeyPatternItems(String regionName) {
        if (StringUtils.isNotEmpty(regionName)) {
            RegionItem regionItem = CacheConfig.getInstance().getRegionItem(regionName);
            if (regionItem == null) {
                logger.error("don't find the " + regionName + " RegionItem in " + CacheConfig.CONFIG_FILE);
                return null;
            }
            return regionItem.getKeyPatternMap();
        } else {
            logger.error("region name must have a value");

        }
        return null;
    }

    /**
     * 给出Region对应的策略表达式Map
     * 
     * @param regionName
     * @return
     */
    public static Map<String, KeyPatternItem> getDefaultKeyKeyPatternItems() {
        RegionItem regionItem = CacheConfig.getInstance().getDefaultRegionItem();
        if (regionItem == null) {
            logger.error("cannot find the default RegionItem in " + CacheConfig.CONFIG_FILE);
            return null;
        }
        return regionItem.getKeyPatternMap();
    }

    /**
     * 通过名字给出MemcachedSource
     * 
     * @param name
     * @return
     */
    public static MemcachedSource getMemcachedSource(String name) {
        return StringUtils.isNotEmpty(name) ? CacheConfig.getInstance().getMemcachedSource(name) : null;
    }

    /**
     * 
     * 
     * @param name
     * @return
     */
    public static String getQueueSourceName(String queueName) {
        if (StringUtils.isBlank(queueName))
            return null;
        QueueItem item = CacheConfig.getInstance().getQueueItem(queueName);
        return item != null ? item.getDatasource() : null;
    }

    
    
    public static MemcachedSource getDefaultQueueMemcachedSource() {
        QueueItem item = CacheConfig.getInstance().getQueueItem("default");
        return item == null ? null: getMemcachedSource(item.getDatasource());
    }
    
    /**
     * 
     */
    /**
     * 通过名字给出MemcachedSource
     * 
     * @param name
     * @return
     */
    public static MemcachedSource getDynamicMemcachedSource() {
        return CacheConfig.getInstance().getDynamicMemcachedSource();
    }

    public static String getStrategyClassName(String regionName) {
        if (StringUtils.isNotEmpty(regionName)) {
            RegionItem regionItem = CacheConfig.getInstance().getRegionItem(regionName);
            if (regionItem != null) {
                return regionItem.getStrategyClass();
            } else {
                logger.error("don't find regionName config: " + regionName);
                return null;
            }
        } else {
            logger.error("regionName is null or empty");
            return null;
        }
    }

    /**
     * 
     * @param regionName
     * @return
     */
    public static String getListenerClass(String regionName) {
        if (StringUtils.isNotEmpty(regionName)) {
            RegionItem regionItem = CacheConfig.getInstance().getRegionItem(regionName);
            if (regionItem != null) {
                return regionItem.getListenerClass();
            } else {
                logger.error("don't find regionName config: " + regionName);
                return null;
            }
        } else {
            logger.error("regionName is null or empty");
            return null;
        }
    }

    /**
     * 
     * @param regionName
     * @return
     */
    public static int getListLimitLen(String regionName) {
        if (StringUtils.isNotEmpty(regionName)) {
            RegionItem regionItem = CacheConfig.getInstance().getRegionItem(regionName);
            if (regionItem != null) {
                return regionItem.getLimitLen();
            } else {
                logger.error("don't find regionName config: " + regionName);
                return 0;
            }
        } else {
            logger.error("regionName is null or empty");
            return 0;
        }
    }    
    /**
     * 检查一个Region是否在配置文件中存在
     * 
     * @param regionName
     * @return
     */
    public static boolean isExistRegion(String regionName) {
        if (StringUtils.isNotEmpty(regionName)) {
            RegionItem regionItem = CacheConfig.getInstance().getRegionItem(regionName);
            return regionItem == null ? false : true;
        }
        return false;
    }

    /**
     * 检查一个Region是否支持本地缓存
     * 
     * @param regionName
     * @return
     */
    public static boolean isLocalCache(String regionName) {
        if (StringUtils.isNotEmpty(regionName)) {
            RegionItem regionItem = CacheConfig.getInstance().getRegionItem(regionName);
            return regionItem == null ? false : regionItem.isLocalCache();
        }
        return false;
    }

    /**
     * 检查一个Region是否支持远程缓存
     * 
     * @param regionName
     * @return
     */
    public static boolean isRemoteCache(String regionName) {
        if (StringUtils.isNotEmpty(regionName)) {
            RegionItem regionItem = CacheConfig.getInstance().getRegionItem(regionName);
            return regionItem == null ? false : regionItem.isRemoteCache();
        }
        return false;
    }    
    /**
     * 检查一个Region是否支持系列化类消息
     * 
     * @param regionName
     * @return
     */
    public static boolean isHasClassInfo(String regionName) {
        if (StringUtils.isNotEmpty(regionName)) {
            RegionItem regionItem = CacheConfig.getInstance().getRegionItem(regionName);
            return regionItem == null ? true : regionItem.isHasClassInfo();
        }
        return true;
    }
}
