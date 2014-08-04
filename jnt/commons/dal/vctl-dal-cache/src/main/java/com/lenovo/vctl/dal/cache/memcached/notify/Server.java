package com.lenovo.vctl.dal.cache.memcached.notify;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.lenovo.vctl.dal.cache.Cache;
import com.lenovo.vctl.dal.cache.client.CacheClient;
import com.lenovo.vctl.dal.cache.memcached.CacheFactoryImpl;
import com.lenovo.vctl.dal.cache.memcached.channel.source.MemcachedSource;
import com.lenovo.vctl.dal.cache.memcached.client.MemCachedClientImpl;
import com.lenovo.vctl.dal.cache.memcached.config.helper.CacheConfigHelper;
import com.lenovo.vctl.dal.cache.memcached.notify.listener.NotifyListenerImpl;
import com.lenovo.vctl.dal.cache.memcached.notify.model.Message;


/**
 * 解析失效的队列
 * 
 * 
 * @author allenshen date: Jul 16, 2009 10:37:05 AM Copyright 2008 Sohu.com Inc.
 *         All Rights Reserved.
 */
public class Server {
    private static Map<String, Boolean> modifyMethods = new HashMap<String, Boolean>();
    static {
        modifyMethods.put("delete", Boolean.TRUE);
        modifyMethods.put("save", Boolean.TRUE);
        modifyMethods.put("remove", Boolean.TRUE);
        modifyMethods.put("update", Boolean.TRUE);
        modifyMethods.put("incr", Boolean.TRUE);
        modifyMethods.put("decr", Boolean.TRUE);
        modifyMethods.put("ladd", Boolean.TRUE);
        modifyMethods.put("radd", Boolean.TRUE);
        modifyMethods.put("setList", Boolean.TRUE);
        modifyMethods.put("removeList", Boolean.TRUE);
    }
    private static Logger logger = Logger.getLogger(Server.class);
    private static ExecutorService executorService = Executors.newFixedThreadPool(1);

    public static void main(String[] args) {
        MemcachedSource source = CacheConfigHelper.getDefaultQueueMemcachedSource();
        if (source != null) {
            CacheClient client = new MemCachedClientImpl(source);
            while (true) {
                String eventData = (String) client.get(NotifyListenerImpl.QUEUE_NAME);
                if (StringUtils.isNotBlank(eventData)) {
                    final Message message = new Message(eventData);
                    System.out.println(message);
                    executorService.execute(new Runnable() {
                        public void run() {
                            processMessage(message);
                        }
                    });

                } else {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            System.out.println("don't find queue default memcachedsource");
        }
    }

    private static void processMessage(Message message) {
        if (message != null) {
            Cache cache = CacheFactoryImpl.getInstance().getCache(message.getRegionName(), true);
            if (cache != null) {
                try {
                    // cache.regListener(null);
                    if (logger.isDebugEnabled()) {
                        logger.debug(message);
                    }
                    if (isModifyMethod(message.getMethodName())) {
                        cache.remove(message.getKeys());
                    } else {
                        if (logger.isDebugEnabled()) {
                            logger.debug(" the method " + message.getMethodName() + " don't process");
                        }
                    }

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        }
    }

    private static boolean isModifyMethod(String methodName) {
        if (StringUtils.isNotBlank(methodName)) {
            return modifyMethods.get(methodName) == null ? false : true;
        } else {
            return false;
        }
    }
}
