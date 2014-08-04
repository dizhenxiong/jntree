package com.lenovo.vctl.dal.dao.route.strategy;

import com.lenovo.vctl.dal.dao.exception.StrategyException;
/**
 * 
 * 
 * @author allenshen date: Nov 28, 2008 4:21:24 PM Copyright 2008 Sohu.com Inc.
 *         All Rights Reserved.
 */
public interface IStrategy {
   public static int STRATEGY_R = 1;
    public static int STRATEGY_W = 2;
  
    public Object ObjectShardingStrategy(Class clazz, Object account, int rw) throws StrategyException;

    public Object ListShardingStrategy(String listName, Object account, int rw) throws StrategyException;

    public Object MapShardingStrategy(String listName, Object account, int rw) throws StrategyException;

    public boolean isReadWrite(Class clazz, Object account) throws StrategyException;
        
    public Object NextListShardingStrategy(String listName,int rw) throws StrategyException;
    
    
    
    
    
    public Object ObjectShardingStrategy(Class clazz, Object account, Object id , int rw) throws StrategyException;

    public Object ListShardingStrategy(String listName, Object account, Object[] params, int rw) throws StrategyException;

    public Object MapShardingStrategy(String listName, Object account, Object[] params, int rw) throws StrategyException;

  //  public boolean isReadWrite(Class clazz, Object account) throws StrategyException;
        
  //  public Object NextListShardingStrategy(String listName,int rw) throws StrategyException;
}
