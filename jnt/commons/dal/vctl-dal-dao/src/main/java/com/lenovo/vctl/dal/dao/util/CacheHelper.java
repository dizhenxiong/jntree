package com.lenovo.vctl.dal.dao.util;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lenovo.vctl.dal.cache.Cache;
import com.lenovo.vctl.dal.cache.exception.CacheException;
import com.lenovo.vctl.dal.cache.memcached.CacheFactoryImpl;
import com.lenovo.vctl.dal.dao.helper.LogHelper;

public  class CacheHelper {
	
	private static Log  log = LogFactory.getLog(CacheHelper.class);

    public static Cache getListCache(String regionName) {
       Cache cache = CacheFactoryImpl.getInstance().getCache(regionName, Boolean.TRUE);
       if(null == cache){
    	   LogHelper.cacheDontConfigured(log, regionName);
       }
       return cache;
    }

    public static Cache getClassCache(Class clazz) {
        if (null == clazz) {
            return null;
        }
        return getListCache(clazz.getName());
    }

    public static Cache getObjectCache(Object object){
        if(null == object){
            return null;
        }
        return getListCache(object.getClass().toString());
    }

    public static void put(String region,String key,Object value) throws CacheException{
    	Cache cache = getListCache(region);
    	if(null != cache){
    	   cache.put(key, value);
    	}
    }


    public static void save(Class objCls,Serializable id, Object obj) throws CacheException{
    	Cache cache = getClassCache(objCls);
    	if(null != cache){
    	   cache.save(id.toString(), obj);
    	}
    }

    public static Object get(String region,String key) throws CacheException{
    	Object obj = null;
    	Cache cache =getListCache(region);
    	if(null!= cache && null != key){
    		obj = cache.get(key);
    	}
    	return obj;
    }
    

    public static Object[] gets(String region,List keyLs) throws CacheException{
       Cache cache = getListCache(region);
       if(null != cache && null != keyLs && keyLs.size() >0){
    	   return cache.get((String[])keyLs.toArray(new String[keyLs.size()]));
       }
       return null;
    }
    
    public static void delete(String region,String key) throws CacheException{  	
    	Cache cache = getListCache(region);
    	if(null != cache && null != key){
    		cache.delete(key);
    	}
    }

    public static void remove(String region,String key) throws CacheException{
    	Cache cache =getListCache(region);
    	if(null != cache){
    		cache.remove(key);
    	}
    }
    public static void update(String region,String key,Object value) throws CacheException{
    	Cache cache = getListCache(region);
    	if(null != cache){
    		cache.update(key, value);
    	}
    }


    public static boolean isDelete(String region,String key) throws CacheException{
    	boolean res = true;
    	Cache cache = getListCache(region);
    	if(null != cache){
    		res = cache.isDelete(key);
    	}
    	return res;
    }


     
}
