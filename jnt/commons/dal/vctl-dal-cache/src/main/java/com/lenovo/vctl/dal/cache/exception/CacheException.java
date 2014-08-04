package com.lenovo.vctl.dal.cache.exception;

/**
 * 
 * 
 * 
 * @author allenshen
 * date: Dec 11, 2008 4:20:12 PM
 * Copyright 2008 Sohu.com Inc. All Rights Reserved.
 */
public class CacheException extends Exception {
    /**
     * 
     */
    private static final long serialVersionUID = 5585853019888729862L;

    public CacheException() {
        super();
    }

    public CacheException(String message) {
        super(message);
    }

    public CacheException(String message, Throwable cause) {
        super(message, cause);
    }


    public CacheException(Throwable cause) {
        super(cause);
    }
}
