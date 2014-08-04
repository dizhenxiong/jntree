package com.lenovo.vctl.dal.cache.memcached.channel.source;

import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.log4j.Logger;

import com.lenovo.vctl.dal.cache.memcached.channel.MemcachedChannel;
import com.lenovo.vctl.dal.cache.memcached.channel.pool.MChannelFactoryImpl;
import com.lenovo.vctl.dal.cache.memcached.channel.pool.MChannellPoolableObjectFactory;

/**
 * 
 * 
 * 
 * @author allenshen
 * date: Dec 12, 2008 4:16:46 PM
 * Copyright 2008 Sohu.com Inc. All Rights Reserved.
 */
public class MemcachedSourceImpl implements MemcachedSource {
    private static Logger logger = Logger.getLogger(MemcachedSourceImpl.class);
    
    protected int maxActive = GenericObjectPool.DEFAULT_MAX_ACTIVE; // 激活多少个
    protected int maxIdle = GenericObjectPool.DEFAULT_MAX_IDLE + 20; // 最多保存多少个
    protected int minIdle = GenericObjectPool.DEFAULT_MIN_IDLE; // 最新保存多少个
    // protected long maxWait = GenericObjectPool.DEFAULT_MAX_WAIT;
    protected long maxWait = 1000l;
    protected boolean testOnBorrow = true;
    protected boolean testOnReturn = false;
    protected long timeBetweenEvictionRunsMillis = GenericObjectPool.DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS;
    protected int numTestsPerEvictionRun = GenericObjectPool.DEFAULT_NUM_TESTS_PER_EVICTION_RUN;
    protected long minEvictableIdleTimeMillis = GenericObjectPool.DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS;
    protected boolean testWhileIdle = false;

    private String host;
    private int port;
    private int timeout;

    private boolean isCreatePool = false;
    private GenericObjectPool mChannelPool;

    public MemcachedSourceImpl(String host, int port, int timeout) {
        this.host = host;
        this.port = port;
        this.timeout = timeout;
    }

    public MemcachedChannel getMemcachedChannel() throws Exception {
        if (!this.isCreatePool) {
            createPool();
        }
        return (MemcachedChannel) mChannelPool.borrowObject();
    }

    private synchronized void createPool() throws Exception {
            if (this.isCreatePool)
                return;
            mChannelPool = new GenericObjectPool();
            mChannelPool.setMaxActive(maxActive);
            mChannelPool.setMaxIdle(maxIdle);
            mChannelPool.setMinIdle(minIdle);
            mChannelPool.setMaxWait(maxWait);
            mChannelPool.setTestOnBorrow(testOnBorrow);
            mChannelPool.setTestOnReturn(testOnReturn);
            mChannelPool.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
            mChannelPool.setNumTestsPerEvictionRun(numTestsPerEvictionRun);
            mChannelPool.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
            mChannelPool.setTestWhileIdle(testWhileIdle);

            mChannelPool.setWhenExhaustedAction(GenericObjectPool.WHEN_EXHAUSTED_BLOCK);

            MChannellPoolableObjectFactory channellPoolableObjectFactory = new MChannellPoolableObjectFactory(
                    new MChannelFactoryImpl(this.host, this.port, this.timeout), this.mChannelPool);
            validateMChannellFactory(channellPoolableObjectFactory);
            this.isCreatePool = true;

    }

    private static void validateMChannellFactory(MChannellPoolableObjectFactory factory) throws Exception {
        MemcachedChannel mChannel = null;
        try {
            mChannel = (MemcachedChannel) factory.makeObject();
            factory.activateObject(mChannel);
            factory.validateObject(mChannel);
            factory.passivateObject(mChannel);
        } finally {
            factory.destroyObject(mChannel);
        }
    }

    public synchronized void close() throws Exception {
        
        GenericObjectPool oldpool = mChannelPool;
        mChannelPool = null;

        try {
            if (oldpool != null) {
                oldpool.close();
            }
        } catch (Exception e) {
            throw new Exception("Cannot close memcached connection pool", e);
        }
        logger.info("close pool completed");
    }

    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public long getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(long maxWait) {
        this.maxWait = maxWait;
    }

    public boolean isTestOnBorrow() {
        return testOnBorrow;
    }

    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public boolean isTestOnReturn() {
        return testOnReturn;
    }

    public void setTestOnReturn(boolean testOnReturn) {
        this.testOnReturn = testOnReturn;
    }

    public long getTimeBetweenEvictionRunsMillis() {
        return timeBetweenEvictionRunsMillis;
    }

    public void setTimeBetweenEvictionRunsMillis(long timeBetweenEvictionRunsMillis) {
        this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
    }

    public int getNumTestsPerEvictionRun() {
        return numTestsPerEvictionRun;
    }

    public void setNumTestsPerEvictionRun(int numTestsPerEvictionRun) {
        this.numTestsPerEvictionRun = numTestsPerEvictionRun;
    }

    public long getMinEvictableIdleTimeMillis() {
        return minEvictableIdleTimeMillis;
    }

    public void setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis) {
        this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
    }

    public boolean isTestWhileIdle() {
        return testWhileIdle;
    }

    public void setTestWhileIdle(boolean testWhileIdle) {
        this.testWhileIdle = testWhileIdle;
    }

    /**
     * 
     * @return
     * @author
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("MemcachedSourceImpl[");
        buffer.append("host = ").append(host);
        buffer.append(" port = ").append(port);
        buffer.append(" timeout = ").append(timeout);
        buffer.append("]");
        return buffer.toString();
    }

    public boolean isDynamic() {
        return false;
    }

}
