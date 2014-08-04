package com.lenovo.vctl.dal.cache.memcached.channel.source;

import com.lenovo.vctl.dal.cache.memcached.channel.MemcachedChannel;


public interface MemcachedSource {
    public MemcachedChannel getMemcachedChannel() throws Exception;
    public boolean isDynamic();
}
