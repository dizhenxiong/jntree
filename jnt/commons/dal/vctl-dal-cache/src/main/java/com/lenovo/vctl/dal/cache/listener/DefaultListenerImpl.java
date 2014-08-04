package com.lenovo.vctl.dal.cache.listener;

import org.apache.log4j.Logger;

import com.lenovo.vctl.dal.cache.Cache;



/**
 * Cache鎿嶄綔鐨勭洃鍚櫒
 * 
 * 
 * @author allenshen date: Dec 16, 2008 3:32:50 PM Copyright 2008 Sohu.com Inc.
 *         All Rights Reserved.
 */

public class DefaultListenerImpl implements Listener {
    private static Logger logger = Logger.getLogger(DefaultListenerImpl.class);

    public DefaultListenerImpl() {

    }

    public void afterListener(Cache cache, Object key, Object value, String operateMethod) {

    }

    public void beforeListener(Cache cache, Object key, Object value, String operateMethod) {
        // try {
        // logger.info((new StringBuilder()).append("beforeListener cache :
        // ").append(cache.getRegion()).append(
        // " key : ").append(ObjectUtils.toString(key, "")).append(
        // " value : ").append(ObjectUtils.toString(value, "")).append(" method:
        // ").append(operateMethod)
        // .toString());
        // } catch (CacheException e) {
        // e.printStackTrace();
        // }
    }

    public void afterListener(Cache cache, Object[] key, Object[] value, String operateMethod) {
        // TODO Auto-generated method stub

    }

    public void beforeListener(Cache cache, Object[] key, Object[] value, String operateMethod) {
        // TODO Auto-generated method stub

    }

    public static void main(String[] args) {

    }
}
