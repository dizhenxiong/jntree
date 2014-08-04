package com.lenovo.vctl.dal.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * 
 * @author allenshen date: Jul 6, 2009 11:54:50 AM Copyright 2008 Sohu.com Inc.
 *         All Rights Reserved.
 */
public interface Listener {
	
	
	public void onSaveBegin(Object account_id,Object entity) throws Exception;
	
	public void onSaveCompleted(Object account_id, Class cls,Serializable id, Object entity) throws Exception;
	
	public void onSaveEnd(Object account_id, Serializable id, Object entity) throws Exception;
	
	public void onSaveError(Object account_id,Serializable id, Object entity);
	
	
	public void onBatchSaveBegin(Object account_id, List objList) throws Exception;
	
	public void onBatchSaveCompleted(Object account_id,Class cls, Map idObjMap) throws Exception;
	
	public void onBatchSaveEnd(Object account_id, List objList) ;

	
	public void onUpdateBegin(Object account_id,Serializable id, Object obj,Object oldObj) throws Exception;	
	
	public void onUpdateCompleted(Object account_id,Serializable id, Object obj,Object oldObj) throws Exception;
	
	public void onUpdateEnd(Object account_id,Serializable id, Object obj,Object oldObj) throws Exception;
	
	public void onUpdateError(Object account_id,Serializable id, Object obj,Object oldObj);
	
	
	
	public void onDeleteBegin(Object account_id, Serializable id,Object obj) throws Exception;
	
	public void onDeleteCompleted(Object account_id, Serializable id,Object obj) throws Exception;
	
	public void onDeleteEnd(Object account_id, Serializable id,Object obj,Object oldObject) throws Exception;

	public void onDeleteError(Object account_id, Serializable id,Object obj);





}
