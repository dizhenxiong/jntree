package com.lenovo.vctl.dal.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.lenovo.vctl.dal.cache.impl.LocalCacheImpl;


import net.sf.ehcache.CacheManager;
/**
 * 提供本地Cache
 * 
 * 
 * @author allenshen
 * date: Dec 16, 2008 3:18:54 PM
 * Copyright 2008 Sohu.com Inc. All Rights Reserved.
 */
public class LocalCacheFactory implements CacheFactory {
    private static Logger logger = Logger.getLogger(LocalCacheFactory.class);
    private static CacheFactory instance;
    private static Map<String, Cache> cacheMap = new ConcurrentHashMap<String, Cache>();
    private CacheManager manager;

    public Cache getCache(String name) {
        if (StringUtils.isEmpty(name)) {
            logger.error("cache must have name!");
            return null;
        }

        Cache cache = cacheMap.get(name);
        if (cache == null) {
            if (!manager.cacheExists(name)) {
                manager.addCache(name);
            }
            net.sf.ehcache.Cache ehCache = manager.getCache(name);
            cache = new LocalCacheImpl(ehCache, name);
            cacheMap.put(name, cache);
        }
        return cache;
    }

    private LocalCacheFactory() {
        manager = CacheManager.getInstance();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                manager.shutdown();
                logger.info("LocalCacheFactory instance shutdown");

            }
        });
    }

    public Cache getCache(String name, boolean isDynamic) {
        return this.getCache(name);
    }

    public void removeCache(String name) {
        if (StringUtils.isEmpty(name)) {
            logger.error("cache must have name!");
        }
        if (cacheMap.containsKey(name)) {
            cacheMap.remove(name);
        }

        if (manager.cacheExists(name)) {
            manager.removeCache(name);
        }
    }

    /**
     * 
     * @return
     */
    public static CacheFactory getInstance() {
        if (instance == null) {
            synchronized (LocalCacheFactory.class) {
                if (instance == null) {
                    instance = new LocalCacheFactory();
                }
            }
        }
        return instance;
    }

}
