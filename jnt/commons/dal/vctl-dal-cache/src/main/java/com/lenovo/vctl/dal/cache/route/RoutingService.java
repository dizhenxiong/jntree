package com.lenovo.vctl.dal.cache.route;

import com.lenovo.vctl.dal.cache.exception.StrategyException;


public interface RoutingService {
    public abstract boolean setRoutingStrategy(String regionName, Object key) throws StrategyException;
}