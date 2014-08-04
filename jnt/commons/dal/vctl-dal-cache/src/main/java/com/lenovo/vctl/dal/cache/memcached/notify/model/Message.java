package com.lenovo.vctl.dal.cache.memcached.notify.model;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

public class Message {
    private String ipAddress;
    private String regionName;
    private String keys;
    private String methodName;
    private long time;

    public Message() {
        // TODO Auto-generated constructor stub
    }

    public Message(String eventStr) {
        if (StringUtils.isNotBlank(eventStr)) {
            String[] str = eventStr.split(",");
            if (str != null && str.length == 5) {
                this.ipAddress = str[0];
                this.regionName = str[1];
                this.keys = str[2];
                this.methodName = str[3];
                this.time = NumberUtils.toLong(str[4], 0);
            }
        }
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getKeys() {
        return keys;
    }

    public void setKeys(String keys) {
        this.keys = keys;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String toEventStr() {
        String message = new StringBuilder().append(StringUtils.defaultString(this.ipAddress, "")).append(",").append(
                this.regionName).append(",").append(this.keys).append(",").append(this.methodName).append(",").append(
                this.time).toString();
        return message;
    }

    /**
     * 
     * @return
     * @author
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("Message[");
        buffer.append("ipAddress = ").append(ipAddress);
        buffer.append(",\n methodName = ").append(methodName);
        buffer.append(",\n regionName = ").append(regionName);
        buffer.append(",\n time = ").append(time);
        buffer.append(",\n keys = ").append(keys);
        buffer.append("]");
        return buffer.toString();
    }
}
