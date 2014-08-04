package com.lenovo.vctl.dal.cache.config.model;

import java.io.Serializable;

/**
 * 
 * 
 * 
 * @author allenshen 
 * date: Nov 27, 2008 2:20:49 PM 
 * Copyright 2008 Sohu.com Inc. All Rights Reserved.
 */
public class DatasourceItem implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -6548768493657025956L;
    private String name;      
    private int timeout;      
    private int port;         
    private String server;    
    private int maxActive;   
    private int maxIdle;      
    private int maxWait;      
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getTimeout() {
        return timeout;
    }
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
    public int getPort() {
        return port;
    }
    public void setPort(int port) {
        this.port = port;
    }
    public String getServer() {
        return server;
    }
    public void setServer(String server) {
        this.server = server;
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
    public int getMaxWait() {
        return maxWait;
    }
    public void setMaxWait(int maxWait) {
        this.maxWait = maxWait;
    }
}
