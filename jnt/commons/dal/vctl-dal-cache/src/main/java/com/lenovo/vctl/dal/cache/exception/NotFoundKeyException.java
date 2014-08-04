package com.lenovo.vctl.dal.cache.exception;

/**
 * 
 * 
 * 
 * @author allenshen date: Dec 11, 2008 4:20:12 PM Copyright 2008 Sohu.com Inc.
 *         All Rights Reserved.
 */
public class NotFoundKeyException extends CacheException {
    /**
     * 
     */
    private static final long serialVersionUID = 5585853019888729862L;

    public NotFoundKeyException() {
        super();
    }

    public NotFoundKeyException(String message) {
        super(message);
    }

    public NotFoundKeyException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundKeyException(Throwable cause) {
        super(cause);
    }
}
