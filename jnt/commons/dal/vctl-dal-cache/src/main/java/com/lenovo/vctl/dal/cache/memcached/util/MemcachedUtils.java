package com.lenovo.vctl.dal.cache.memcached.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
/**
 * 
 * 
 * 
 * @author allenshen
 * date: Dec 16, 2008 3:39:04 PM
 * Copyright 2008 Sohu.com Inc. All Rights Reserved.
 */
public final class MemcachedUtils {
    public static String sanitizeKey(boolean sanitizeKeys, String key) throws UnsupportedEncodingException {
        return (sanitizeKeys) ? URLEncoder.encode(key, "UTF-8") : key;
    }
}
