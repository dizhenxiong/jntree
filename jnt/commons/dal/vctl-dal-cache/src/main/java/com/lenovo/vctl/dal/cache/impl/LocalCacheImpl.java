package com.lenovo.vctl.dal.cache.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;

import com.lenovo.vctl.dal.cache.Cache;
import com.lenovo.vctl.dal.cache.ListResult;
import com.lenovo.vctl.dal.cache.exception.CacheException;
import com.lenovo.vctl.dal.cache.exception.NotFoundKeyException;
import com.lenovo.vctl.dal.cache.listener.Listener;
import com.lenovo.vctl.dal.cache.utils.DebugTimeUtils;

import net.sf.ehcache.Element;

/**
 * 
 * 
 * 
 * @author allenshen date: Dec 16, 2008 3:32:23 PM Copyright 2008 Sohu.com Inc.
 *         All Rights Reserved.
 */
public class LocalCacheImpl implements Cache {
	private static Logger logger = Logger.getLogger(LocalCacheImpl.class);
	private net.sf.ehcache.Cache ehCache;
	private String region;
	private Listener listener;
	private int limitLen = 300;
	private int initLen = 100;

	public int getLimitLen() {
		return limitLen;
	}

	public void setLimitLen(int limitLen) {
		this.limitLen = limitLen;
	}

	public int getInitLen() {
		return initLen;
	}

	public void setInitLen(int initLen) {
		this.initLen = initLen;
	}

	public LocalCacheImpl(net.sf.ehcache.Cache ehCache, String regionName) {
		this.ehCache = ehCache;
		this.region = regionName;
	}

	public net.sf.ehcache.Cache getEhCache() {
		return ehCache;
	}

	public void setEhCache(net.sf.ehcache.Cache ehCache) {
		this.ehCache = ehCache;
	}

	public boolean delete(String key) throws CacheException {
		return this.ehCache.remove(key);
	}

	public boolean remove(String key) throws CacheException {
		return this.ehCache.remove(key);
	}

	public Object get(String key) throws CacheException {
		Element element = this.ehCache.get(key);
		return element == null ? null : element.getObjectValue();
	}

	public String getRegion() throws CacheException {
		return this.region;
	}

	public boolean put(String key, Object value) throws CacheException {
		Element element = new Element(key, value);
		this.ehCache.put(element);
		return true;
	}
	
	public boolean put(String key, Object value, String dispatchKey) throws CacheException {
		return put(key, value);
	}

	public void regListener(Listener listener) {
		this.listener = listener;

	}

	public boolean update(String key, Object value) throws CacheException {
		return this.put(key, value);
	}

	public boolean save(String key, Object value) throws CacheException {
		return this.put(key, value);
	}

	public boolean isDelete(String key) throws CacheException {
		return false;
	}

	public Object[] get(String[] key) throws CacheException {
		if (ArrayUtils.isEmpty(key)) {
			return null;
		}

		Object[] lresult = new Object[key.length];
		for (int i = 0; i < key.length; i++) {
			lresult[i] = this.get(key[i]);
		}
		return lresult;

	}

	public boolean save(Map<String, Object> objectsMap) throws CacheException {
		return true;
	}

	@Override
	public long decr(String key, long inc) throws CacheException {
		return 0;
	}

	@Override
	public long incr(String key, long inc) throws CacheException {
		Object o = this.get(key);
		if (o != null && inc != 0l) {
			Long lResult = NumberUtils.toLong(o.toString(), 0l);
			lResult = lResult + inc;
			this.put(key, lResult);
			return lResult;
		}
		if (o == null) {
			return -1l;
			//this.put(key, Long.valueOf(inc));
			//return inc;
		}
		return NumberUtils.toLong(ObjectUtils.toString(o, "0"), 0l);
	}

	@Override
	public ListResult ladd(String key, String value) throws NotFoundKeyException {
		try {
			this.remove(key);
			return ListResult.LIST_OK;
		} catch (CacheException e) {
			e.printStackTrace(System.err);
			return ListResult.LIST_ERROR;
		}
	}

	@Override
	public List<String> lrange(String key, int beg, int end) throws NotFoundKeyException {
		List<String> lResult = null;
        if (beg > end || end == 0 ) {
            return lResult;
        }

        DebugTimeUtils.begTime();
        if (StringUtils.isEmpty(key)) {
            logger.info("region:  key is empty or null");
            return null;
        }
        Object o = null;
        try {
            o = this.get(key);
           
            if (o != null) {
                String resultStr = (String) o;
                String[] strings = StringUtils.split(resultStr, ',');
                if (!ArrayUtils.isEmpty(strings)) {
                    String[] tempArray = (String[]) ArrayUtils.subarray(strings, beg, end);
                    if (!ArrayUtils.isEmpty(tempArray)) {
                        lResult = new ArrayList<String>();
                        for (int i = 0; i < tempArray.length; i++) {
                            lResult.add(tempArray[i]);
                        }
                        if (tempArray.length > this.limitLen) {
                            logger.info("list too long , begin remove");
                            this.remove(key);
                        }
                    }
                }
            }

        } catch (Exception e) {
            throw new NotFoundKeyException(e);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("get time : " + DebugTimeUtils.getDistanceTime());
        }
        return lResult;
	}

	@Override
	public ListResult radd(String key, String value) throws NotFoundKeyException {
		try {
			this.remove(key);
			return ListResult.LIST_OK;
		} catch (CacheException e) {
			e.printStackTrace(System.err);
			return ListResult.LIST_ERROR;
		}

	}

	@Override
	public ListResult removeList(String key) throws CacheException {
		// TODO Auto-generated method stub
//		return this.removeList(key);
		return null;
	}

	@Override
	public ListResult setList(String key, List<Object> values) {
		if (CollectionUtils.isNotEmpty(values)) {
			if (values.size() > this.limitLen) {
				return ListResult.LIST_LIMT;
			}
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < values.size(); i++) {
				Object value = values.get(i);
				if (sb.length() > 0) {
					sb.append(",").append(ObjectUtils.toString(value, ""));
				} else {
					sb.append(ObjectUtils.toString(value, ""));
				}
			}
			try {
				this.put(key, sb.toString());
				return ListResult.LIST_OK;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} else {
			logger.info("value list is empty or null");
			return ListResult.LIST_OK;
		}
	}

	@Override
	public Integer lsize(String key) throws CacheException {
		Integer iResult = -1;

        DebugTimeUtils.begTime();
        if (StringUtils.isEmpty(key)) {
            logger.info("region:  key is empty or null");
            return null;
        }
        Object o = null;
        try {
            o = this.get(key);
           
            if (o != null) {
                String resultStr = (String) o;
                String[] strings = StringUtils.split(resultStr, ',');
                if (!ArrayUtils.isEmpty(strings)) {
                    if (!ArrayUtils.isEmpty(strings)) {
                        iResult = strings.length;
                    }
                }
            }

        } catch (Exception e) {
            throw new NotFoundKeyException(e);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("get time : " + DebugTimeUtils.getDistanceTime());
        }
        return iResult;
	}

}
