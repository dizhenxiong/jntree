package com.lenovo.vctl.apps.image.upload.util;


import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class PropertiesUtil {
    static Log log = LogFactory.getLog(PropertiesUtil.class);
    private static Map<String, String> pMap;
    private static String propertiesFiles = "base.properties,domain.properties";
    private static Object lock = new Object();
    static{
        if (pMap == null) {
            synchronized (lock) {
                if (pMap == null) {
                    pMap = loadProperties();
                    String s = "load properties from " + propertiesFiles + ":";
                    for (String tempkey : pMap.keySet()) {
                        s += tempkey + "=" + pMap.get(tempkey) + ",";
                    }
                    log.info(s);
                }
            }
        }
    }
    public static String get(String key) {
        return pMap.get(key);
    }

    public static String getTrimString(String key) {
        String string = pMap.get(key);
        return string != null ? string.trim() : null;
    }

    private static Map<String, String> loadProperties() {
        String[] fs = propertiesFiles.split(",");
        Map<String, String> map = new HashMap<String, String>();
        for (int i = 0; i < fs.length; i++) {
            Properties p = new Properties();
            Resource resource = new ClassPathResource(fs[i]);
            InputStream in = null;
            try {
                in = resource.getInputStream();
                p.load(in);
            } catch (IOException e) {
                log.error("error=load properties file fail;file=" + fs[i]+" "+e.getMessage(), e);
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                    }
                }
            }
            p.entrySet();
            Set<Object> keyset = p.keySet();
            for (Object key : keyset) {
                map.put((String) key, p.getProperty((String) key));
            }

        }
        return map;
    }

    public static String getFileProperties(String file, String key) {
        return getResourceProperties(file).getProperty(key);
    }

    public static String get(String key, String defaultMsg) {
        String v= pMap.get(key);
        if(v==null)
            return defaultMsg;
        else
            return v;
    }

    public static int getInt(String key) {
        String v = pMap.get(key);
        return Integer.parseInt(v);
    }

    public static int getInt(String key, int defaultVal) {
        String v = pMap.get(key);
        if (v != null) {
            return Integer.parseInt(v);
        } else
            return defaultVal;
    }

    /**
     * 返回classpath下指定文件名的properties
     *
     * @param filename
     * @return Properties
     */
    public static Properties getResourceProperties(String filename) {
        Resource resource = new ClassPathResource(filename);
        InputStream in = null;
        try {
            in = resource.getInputStream();
            Properties p = new Properties();
            p.load(in);
            return p;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }
    }
}
