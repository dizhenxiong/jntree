package com.lenovo.vctl.dal.cache.memcached.channel.pool;

import java.io.IOException;

import com.lenovo.vctl.dal.cache.memcached.channel.MemcachedChannel;

public interface MChannelFactory {
    public MemcachedChannel createMemcachedChannel() throws IOException;
}
