package com.lenovo.vctl.dal.cache.memcached.handler;

import com.lenovo.vctl.dal.cache.client.CacheClient;



/**
 * 
 * 
 * 
 * @author allenshen
 * date: Nov 26, 2008 3:47:06 PM
 * Copyright 2008 Sohu.com Inc. All Rights Reserved.
 */
public interface ErrorHandler {

    /**
     * Called for errors thrown during initialization.
     */
    public void handleErrorOnInit( final CacheClient client ,
                                   final Throwable error );

    /**
     * Called for errors thrown during {@link CacheClient#get(String)} and related methods.
     */
    public void handleErrorOnGet( final CacheClient client ,
                                  final Throwable error ,
                                  final String cacheKey );

    /**
     * Called for errors thrown during {@link CacheClient#getMulti(String)} and related methods.
     */
    public void handleErrorOnGet( final CacheClient client ,
                                  final Throwable error ,
                                  final String[] cacheKeys );

    /**
     * Called for errors thrown during {@link CacheClient#set(String,Object)} and related methods.
     */
    public void handleErrorOnSet( final CacheClient client ,
                                  final Throwable error ,
                                  final String cacheKey );

    /**
     * Called for errors thrown during {@link CacheClient#delete(String)} and related methods.
     */
    public void handleErrorOnDelete( final CacheClient client ,
                                     final Throwable error ,
                                     final String cacheKey );

    /**
     * Called for errors thrown during {@link CacheClient#flushAll()} and related methods.
     */
    public void handleErrorOnFlush( final CacheClient client ,
                                    final Throwable error );

    /**
     * Called for errors thrown during {@link CacheClient#stats()} and related methods.
     */
    public void handleErrorOnStats( final CacheClient client ,
                                    final Throwable error );

} // interface
