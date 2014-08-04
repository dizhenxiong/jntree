package com.lenovo.vctl.dal.cache.memcached.notify.listener;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;

import com.lenovo.vctl.dal.cache.Cache;
import com.lenovo.vctl.dal.cache.client.CacheClient;
import com.lenovo.vctl.dal.cache.exception.CacheException;
import com.lenovo.vctl.dal.cache.listener.Listener;
import com.lenovo.vctl.dal.cache.memcached.channel.source.MemcachedSource;
import com.lenovo.vctl.dal.cache.memcached.client.MemCachedClientImpl;
import com.lenovo.vctl.dal.cache.memcached.config.helper.CacheConfigHelper;
import com.lenovo.vctl.dal.cache.memcached.notify.model.Message;


/**
 * Cache操作的监听器
 * 
 * 
 * @author allenshen date: Dec 16, 2008 3:32:50 PM Copyright 2008 Sohu.com Inc.
 *         All Rights Reserved.
 */

public class NotifyListenerImpl implements Listener {
    private static Logger logger = Logger.getLogger(NotifyListenerImpl.class);

    private static CacheClient cachedClient = null;
    static{
        MemcachedSource source = CacheConfigHelper.getDefaultQueueMemcachedSource();
        if (source != null) {
            cachedClient = new MemCachedClientImpl(source);
        }

        try {
            locahostIp = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            if (logger.isDebugEnabled()) {
                logger.error(e.getMessage());
            }
        }

    }
    public final static String QUEUE_NAME = "listener";
    private static String locahostIp = "";

    public NotifyListenerImpl() {
//        if (cachedClient == null) {
//            MemcachedSource source = CacheConfigHelper.getDefaultQueueMemcachedSource();
//            if (source != null) {
//                cachedClient = new MemCachedClientImpl(source);
//            }
//        }
//        if (StringUtils.isBlank(locahostIp)) {
//            try {
//                locahostIp = InetAddress.getLocalHost().getHostAddress();
//            } catch (UnknownHostException e) {
//                if (logger.isDebugEnabled()) {
//                    logger.error(e.getMessage());
//                }
//            }
//        }
    }

    public void afterListener(Cache cache, Object key, Object value, String operateMethod) {
        try {
            if (logger.isDebugEnabled()) {
                logger.debug((new StringBuilder()).append("afterListener cache :         ").append(cache.getRegion())
                        .append(" key : ").append(ObjectUtils.toString(key, "")).append(" value : ").append(
                                ObjectUtils.toString(value, "")).append(" method:         ").append(operateMethod)
                        .toString());
            }
            if (cachedClient != null) {
                Message message = new Message();
                message.setIpAddress(locahostIp);
                message.setKeys(ObjectUtils.toString(key,""));
                message.setMethodName(operateMethod);
                message.setRegionName(cache.getRegion());
                message.setTime(System.currentTimeMillis());
                cachedClient.set(QUEUE_NAME, message.toEventStr());
            }
        } catch (CacheException e) {
            e.printStackTrace();
        }
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
        try {
            System.out.println(InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
