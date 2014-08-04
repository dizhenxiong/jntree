package com.lenovo.vctl.dal.cache.client;

import java.util.Date;

import com.lenovo.vctl.dal.cache.exception.CacheException;

/**
 * 
 * 
 * 
 * @author allenshen date: Jan 16, 2009 10:59:46 AM Copyright 2008 Sohu.com Inc.
 *         All Rights Reserved.
 */
public interface CacheClient {

    // public abstract void setErrorHandler(ErrorHandler errorHandler);

    /**
     * 会在Cache�?打上删除的标�?Deletes an object from cache given cache key.
     * 
     * @param key
     *            the key to be removed
     * @return <code>true</code>, if the data was deleted successfully
     */
    public abstract boolean delete(String key);

    /**
     * 
     * remove an object from cache given cache key.
     * 
     * @param key
     *            the key to be removed
     * @return <code>true</code>, if the data was deleted successfully
     */
    public abstract boolean remove(String key);

    /**
     * Deletes an object from cache given cache key and expiration date.
     * 
     * @param key
     *            the key to be removed
     * @param expiry
     *            when to expire the record.
     * @return <code>true</code>, if the data was deleted successfully
     */
    public abstract boolean delete(String key, Date expiry);

    /**
     * Stores data on the server; only the key and the value are specified.
     * 
     * @param key
     *            key to store data under
     * @param value
     *            value to store
     * @return true, if the data was successfully stored
     */
    public abstract boolean set(String key, Object value);

    /**
     * Stores data on the server; the key, value, and an expiration time are
     * specified.
     * 
     * @param key
     *            key to store data under
     * @param value
     *            value to store
     * @param expiry
     *            when to expire the record
     * @return true, if the data was successfully stored
     */
    public abstract boolean set(String key, Object value, Date expiry);

    /**
     * Updates data on the server; only the key and the value are specified.
     * 
     * @param key
     *            key to store data under
     * @param value
     *            value to store
     * @return true, if the data was successfully stored
     */
    public abstract boolean replace(String key, Object value);

    /**
     * Updates data on the server; the key, value, and an expiration time are
     * specified.
     * 
     * @param key
     *            key to store data under
     * @param value
     *            value to store
     * @param expiry
     *            when to expire the record
     * @return true, if the data was successfully stored
     */
    public abstract boolean replace(String key, Object value, Date expiry);

    /**
     * Retrieve a key from the server, using a specific hash.
     * 
     * If the data was compressed or serialized when compressed, it will
     * automatically<br/> be decompressed or serialized, as appropriate.
     * (Inclusive or)<br/> <br/> Non-serialized data will be returned as a
     * string, so explicit conversion to<br/> numeric types will be necessary,
     * if desired<br/>
     * 
     * @param key
     *            key where data is stored
     * @return the object that was previously stored, or null if it was not
     *         previously stored
     */
    public abstract Object get(String key);

    /**
     * Invalidates the entire cache.
     * 
     * Will return true only if succeeds in clearing all servers.
     * 
     * @return success true/false
     */

    /**
     * Retrieve multiple objects from the memcache.
     * 
     * This is recommended over repeated calls to {@link #get(String) get()},
     * since it<br/> is more efficient.<br/>
     * 
     * @param keys
     *            String array of keys to retrieve
     * @return Object array ordered in same order as key array containing
     *         results
     */
    public Object[] getMultiArray(String[] keys);

    public abstract boolean flushAll();

    public abstract boolean isDynamic();

    /**
     * 增加数据，必须保证Key存在，如果不存在发回-1;
     * 
     * @param key
     * @param inc
     * @return
     * @throws CacheException
     */
    public long incr(String key, long inc);

    /**
     * 减少数据，必须保证Key存在，如果不存在返回-1，数据降�?为止�?
     * 
     * @param key
     * @param inc
     * @return
     * @throws CacheException
     */
    public long decr(String key, long inc);

    public boolean prepend(String key, String value);

    public boolean append(String key, String value);
    
    public boolean setList(String key, String values);
}
