package com.lenovo.vctl.dal.cache.route;

import org.apache.log4j.Logger;

public final class ContextHolder {
    private static Logger logger = Logger.getLogger(ContextHolder.class);
    private static final ThreadLocal<Object> contextHolder = new ThreadLocal<Object>();

    public static void setCachdName(Object memcachedName) {
        contextHolder.set(memcachedName);
    }

    public static Object getMemcachedName() {
//        if (logger.isDebugEnabled()) {
//            logger.debug("current meached is: " +contextHolder.get() );
//        }
        return contextHolder.get();
    }

    public static void clearDataSource() {
        contextHolder.remove();
    }
}
