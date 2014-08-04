package com.lenovo.vctl.dal.cache.memcached.channel.source;

import java.util.Map;

import org.apache.log4j.Logger;

import com.lenovo.vctl.dal.cache.memcached.channel.MemcachedChannel;
import com.lenovo.vctl.dal.cache.route.ContextHolder;

public class RoutingMemcachedSource implements MemcachedSource {
    private static Logger logger = Logger.getLogger(RoutingMemcachedSource.class);
    private Map<String, MemcachedSource> resolvedMemcachedSources = null;
    private MemcachedSource resolvedDefaultMemcachedSource;

    public MemcachedChannel getMemcachedChannel() throws Exception {
        return determineTargetMemcachedSource().getMemcachedChannel();
    }

    public void setResolvedMemcachedSources(Map<String, MemcachedSource> resolvedMemcachedSources) {
        this.resolvedMemcachedSources = resolvedMemcachedSources;
    }

    protected MemcachedSource determineTargetMemcachedSource() {
        if (this.resolvedMemcachedSources == null) {
            throw new RuntimeException("MemcachedSource router not initialized");
        }
        Object lookupKey = determineCurrentLookupKey();
        MemcachedSource memcachedSource = (MemcachedSource) this.resolvedMemcachedSources.get(lookupKey);
        if (memcachedSource == null) {
            memcachedSource = this.resolvedDefaultMemcachedSource;
        }
        if (memcachedSource == null) {
            throw new IllegalStateException("Cannot determine target memcachedSource for lookup key [" + lookupKey
                    + "]");
        }
        return memcachedSource;
    }

    protected Object determineCurrentLookupKey() {
        Object memcachedName = ContextHolder.getMemcachedName();
//        if (logger.isDebugEnabled()) {
//            logger.info("current memcachedName is: " + ObjectUtils.toString(memcachedName, ""));
//        }
        return memcachedName;
    }

    public boolean isDynamic() {
        return true;
    }
}
