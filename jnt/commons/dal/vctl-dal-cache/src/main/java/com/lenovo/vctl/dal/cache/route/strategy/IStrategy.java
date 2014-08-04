package com.lenovo.vctl.dal.cache.route.strategy;

import com.lenovo.vctl.dal.cache.exception.StrategyException;

/**
 * 策略接口定义
 * 
 * 
 * @author allenshen
 * date: Dec 16, 2008 3:34:12 PM
 * Copyright 2008 Sohu.com Inc. All Rights Reserved.
 */
public interface IStrategy {
    /**
     * 根据Region的名字和CACHE中Key来决定用�?��CACHE(memcachedInstance)
     * 
     * @param regionName
     * @param key
     * @return
     */
    public Object playStrategy(String regionName, Object key) throws StrategyException;
}
