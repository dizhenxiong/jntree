package com.lenovo.vctl.dal.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.lenovo.vctl.dal.dao.model.SqlInfo;


/**
 * 和数据库进行操作的接口
 * @author arthurkang
 *
 */

public interface DBAgent {

	//执行数据库更新的操作
	
	public Serializable save(Object account_id, Object object)throws Exception;
	
	public Map save(Object account_id,final List obs) throws Exception;
	
	public boolean delete(Object account_id, Object obj)throws Exception;
		
	public boolean update(Object account_id, Object object) throws Exception;
	
	public Object get(Object account_id, Class clazz, Serializable id) throws Exception;
	
	public int count(Object account_id,String listName, final SqlInfo sqlInfo) throws Exception;
	
	public List getIdList(Object account_id,String listName, final SqlInfo sqlInfo,final Integer start,final Integer count) throws Exception;
	
	public Object getMapping(Object account_id,String listName, final SqlInfo sqlInfo) throws Exception;
	
	public List getObjectList(Object account_id,String listName, final SqlInfo sqlInfo) throws Exception;
 
	public  List getEntityList(Object account_id,final Class cls, final SqlInfo sqlInfo,int strategy) throws Exception;

}


