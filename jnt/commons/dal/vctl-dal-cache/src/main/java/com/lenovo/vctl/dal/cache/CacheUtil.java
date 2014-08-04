package com.lenovo.vctl.dal.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * 
 * 
 * 
 * @author allenshen date: Dec 11, 2008 3:03:28 PM Copyright 2008 Sohu.com Inc.
 *         All Rights Reserved.
 */
public final class CacheUtil {
    private static Logger logger = Logger.getLogger(CacheUtil.class);
    private final static String DELIMITER = "_";
    private static Map<String, String> shortNameMap = new ConcurrentHashMap<String, String>();
    private final static String DELETE_SUFFIX="delmark";


    public static String keyEncode(String regionName, String sKey) {
            return (new StringBuilder()).append(shortRegionName(regionName)).append(DELIMITER).append(sKey).toString();
    }


    public static String keyDecode(String regionName, String keyEncode) {
            String shortName = shortRegionName(regionName);
            String sResult = keyEncode.substring(0, Math.min(shortName.length(), keyEncode.length()));
            if (shortName.equals(sResult)) {
                return keyEncode.substring(shortName.length() + DELIMITER.length());
            } else {
                logger.error("don't decode keyEncode " + keyEncode);
                return null;
            }
    }

  
    public static String shortRegionName(String regionName) {
        String shortName = shortNameMap.get(regionName);
        if (shortName != null) {
            return shortName;
        } else {
            int iPos = regionName.lastIndexOf(".");
            if (iPos > 0) {
                String sTemp = regionName.substring(iPos + 1);
                shortNameMap.put(regionName, sTemp);
                return sTemp;
            }
        }
        return regionName;

    }

    public static String delMarkKey(String regionName, String sKey) {
            return (new StringBuilder()).append(shortRegionName(regionName)).append(DELIMITER).append(sKey).append(DELIMITER).append(DELETE_SUFFIX).toString();
    }
    public static void main(String[] args) {
        System.out.println(keyEncode("com.sohu.com.photo", "156"));
        System.out.println(delMarkKey("com.sohu.com.photo", "1"));
        System.out.println(keyDecode("com.sohu.com.photo", "photo_156"));
        System.out.println(shortRegionName("com.sohu.com.photo"));

    }
}
