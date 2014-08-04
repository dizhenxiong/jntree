package com.lenovo.vctl.dal.cache.utils;
/**
 * 
 * 
 * 
 * @author allenshen
 * date: Dec 18, 2008 2:21:16 PM
 * Copyright 2008 Sohu.com Inc. All Rights Reserved.
 */
public final class DebugTimeUtils {
    private static ThreadLocal<Long> preTime = new ThreadLocal<Long>();

    public static void begTime() {
        preTime.set(System.currentTimeMillis());
    }

    public static long getDistanceTime() {
        return System.currentTimeMillis() - preTime.get();
    }
}
