package com.lenovo.vctl.dal.cache;

public interface CacheFactory {
    public Cache getCache(String name);

    public Cache getCache(String name, boolean isDynamic);

    public void removeCache(String name);
}
