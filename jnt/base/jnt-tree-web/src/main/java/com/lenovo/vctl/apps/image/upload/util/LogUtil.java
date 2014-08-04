package com.lenovo.vctl.apps.image.upload.util;


import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;

public class LogUtil {

    public static final String PEFIX_INFO = "=== ";
    public static final String PEFIX_ERROR = "XXX ";

    public static void info(Object msg, Log log) {
        if (log.isInfoEnabled()) {
            log.info(PEFIX_INFO + msg);
        }
    }

    public static void info(Object msg, Exception e, Log log) {
        if (log.isInfoEnabled()) {
            e.printStackTrace();
            log.info(PEFIX_INFO + msg);
        }
    }

    public static void error(Object msg, Log log) {
        if (log.isInfoEnabled()) {
            log.error(PEFIX_ERROR + msg);
        }
    }

    public static void error(Object msg, Exception e, Log log) {
        if (log.isInfoEnabled()) {
            e.printStackTrace();
            log.error(PEFIX_ERROR + msg);
        }
    }

    public static void debug(Object msg, Log log) {
        if (log.isDebugEnabled()) {
            log.debug(msg);
        }
    }

    public static void debug(Object msg, Exception e, Log log) {
        if (log.isDebugEnabled()) {
            e.printStackTrace();
            log.debug(msg);
        }
    }

    /**
     * functions for BI log
     */
    public static void statInfo(Log log, String actionName, String... values) {
        if (null == log || StringUtils.isEmpty(actionName) || ArrayUtils.isEmpty(values)) {
            return;
        }

        StringBuilder sb = new StringBuilder();
        //action
        sb.append(actionName);

        for(String value : values) {
            sb.append(ObjectUtils.toString(value, ""));
        }

        //log into file
        log.info(sb.substring(0, sb.length()-1));
    }

    /**
     * functions for BI log
     */
    public static void statInfo(Log log, String actionName, List<String> list) {
        if (null == log || StringUtils.isEmpty(actionName) || list == null || list.isEmpty()) {
            return;
        }
        statInfo(log,actionName,list.toArray(new String[0]));
    }


}
