package com.lenovo.vctl.dal.cache.listener;

import com.lenovo.vctl.dal.cache.Cache;



/**
 * 
 * 
 * 
 * @author allenshen date: Dec 12, 2008 5:47:37 PM Copyright 2008 Sohu.com Inc.
 *         All Rights Reserved.
 */
public interface Listener {
    public abstract void beforeListener(Cache cache, Object key, Object value, String operateMethod);

    public abstract void afterListener(Cache cache, Object key, Object value, String operateMethod);
    
    
    public abstract void beforeListener(Cache cache, Object[] key, Object[] value, String operateMethod);

    public abstract void afterListener(Cache cache, Object[] key, Object[] value, String operateMethod);    

}
