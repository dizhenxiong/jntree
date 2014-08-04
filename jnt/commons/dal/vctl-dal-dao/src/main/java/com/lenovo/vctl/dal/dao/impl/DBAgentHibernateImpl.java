package com.lenovo.vctl.dal.dao.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.lenovo.vctl.dal.dao.DBAgent;
import com.lenovo.vctl.dal.dao.config.helper.DaoHelper;
import com.lenovo.vctl.dal.dao.exception.DaoException;
import com.lenovo.vctl.dal.dao.model.ScalarInfo;
import com.lenovo.vctl.dal.dao.model.SqlInfo;
import com.lenovo.vctl.dal.dao.util.ObjectUtil;

public class DBAgentHibernateImpl implements DBAgent {

	private static DBAgent dbAgent;
	
	public static final int DEFAULT_SIZE = 5000;
	
	private SessionFactory sessionFactory;
	
	private HibernateTemplate hibernateTemplate;


	private DBAgentHibernateImpl() {
		sessionFactory = DaoHelper.getSessionFactory();
		hibernateTemplate = new HibernateTemplate(sessionFactory);
	}

	public static DBAgent getInstance() {
		if (null == dbAgent) {
			 synchronized (DBAgentHibernateImpl.class) {
					dbAgent = new DBAgentHibernateImpl();
			}
	   }
		return dbAgent;
	}

	public Serializable save(Object account_id, Object object)throws Exception {
		Serializable id = null;
		return hibernateTemplate.save(object);
	}
	
	public Map save(Object account_id,final List obs) throws Exception{
		Map	map = (Map)hibernateTemplate.execute(new HibernateCallback(){
				public Object doInHibernate(Session session)throws HibernateException, SQLException {
					Map idObjMap = new HashMap();
					Transaction trans = session.beginTransaction();
					try {
					  Serializable id = null;
					  for(Object obj : obs ){
						id = session.save(obj);
						idObjMap.put(id, obj);
					  }
					  session.flush();
					  session.clear();
					  trans.commit();
					}
					catch(Exception e){
						trans.rollback();
					}
					return idObjMap;
				}
				
			});
		return map;
	}
	
	public boolean update(Object account_id, Object object) throws Exception {
		hibernateTemplate.update(object);
		return true;
	}
	
	public boolean delete(Object account_id,Object obj)throws Exception {
//		Serializable id = ObjectUtil.getObjectId(obj);
		hibernateTemplate.delete(obj);
		return true;
    }
	
	public int count(Object account_id, String listName, final SqlInfo sqlInfo)throws Exception {
		Object count =hibernateTemplate.execute(new HibernateCallback() {
			public Object doInHibernate(Session session)throws HibernateException, SQLException {
				    SQLQuery  query = session.createSQLQuery(sqlInfo.getSql());
					if (null != sqlInfo.getParams() && sqlInfo.getParams().length > 0) {
						int i = 0;
						for (Object id : sqlInfo.getParams()) {
							if (null != id) {
								query.setParameter(i++, id);
							}
						}
					}
					return query.uniqueResult();
				}
			});
       if(null == count){
    	   return 0;
       }
       else{
           
    	   return new Long(count+"").intValue();
       }
    }

	public Object get(Object account_id, Class clazz, Serializable id) throws DaoException {
        return hibernateTemplate.get(clazz, id);
	}

	public List getIdList(Object account_id,String listName, final SqlInfo sqlInfo,final Integer start,final Integer count) throws Exception {
		List list = null;
		list = (List) hibernateTemplate.execute(new HibernateCallback() {
			public Object doInHibernate(Session session)throws HibernateException, SQLException {
				    SQLQuery  query = session.createSQLQuery(sqlInfo.getSql());
				    if(null != sqlInfo.getScalarList() && sqlInfo.getScalarList().size() >0 ){
				      for(ScalarInfo scaInfo : sqlInfo.getScalarList()){
				    	 if(null == scaInfo.getColumnType()){
				    		 query.addScalar(scaInfo.getColumnName());
				    	 }
				    	 else{
				    		 query.addScalar(scaInfo.getColumnName(), scaInfo.getColumnType());
				    	 }
				     }
				    }
					if (null != sqlInfo.getParams() && sqlInfo.getParams().length > 0) {
						int i = 0;
						for (Object id : sqlInfo.getParams()) {
							if (null != id) {
								query.setParameter(i++, id);
							}
						}
					}
					query.setFirstResult(start.intValue());
					query.setMaxResults(count.intValue());
					return query.list();
				}
			});
			if(null == list){
				list = new ArrayList();
			}

		return list;
	}	
	
	public Object getMapping(Object account_id,String listName, final SqlInfo sqlInfo) throws Exception{
		return  hibernateTemplate.execute(new HibernateCallback() {
			public Object doInHibernate(Session session)throws HibernateException, SQLException {
				    SQLQuery  query = session.createSQLQuery(sqlInfo.getSql());
				    if(null != sqlInfo.getScalarList() && sqlInfo.getScalarList().size() >0 ){
				      for(ScalarInfo scaInfo : sqlInfo.getScalarList()){
				    	 if(null == scaInfo.getColumnType()){
				    		 query.addScalar(scaInfo.getColumnName());
				    	 }
				    	 else{
				    		 query.addScalar(scaInfo.getColumnName(), scaInfo.getColumnType());
				    	 }
				     }
				    }
					if (null != sqlInfo.getParams() && sqlInfo.getParams().length > 0) {
						int i = 0;
						for (Object id : sqlInfo.getParams()) {
							if (null != id) {
								query.setParameter(i++, id);
							}
						}
					}
					return query.uniqueResult();
				}
			});
	}	
	
	public List getObjectList(Object account_id,String listName, final SqlInfo sqlInfo) throws Exception {
		List list =(List) hibernateTemplate.execute(new HibernateCallback() {
			public Object doInHibernate(Session session)throws HibernateException, SQLException {
				    SQLQuery  query = session.createSQLQuery(sqlInfo.getSql());
				    if(null != sqlInfo.getScalarList() && sqlInfo.getScalarList().size() >0 ){
				      for(ScalarInfo scaInfo : sqlInfo.getScalarList()){
				    	 if(null == scaInfo.getColumnType()){
				    		 query.addScalar(scaInfo.getColumnName());
				    	 }
				    	 else{
				    		 query.addScalar(scaInfo.getColumnName(), scaInfo.getColumnType());
				    	 }
				     }
				    }
					if (null != sqlInfo.getParams() && sqlInfo.getParams().length > 0) {
						int i = 0;
						for (Object id : sqlInfo.getParams()) {
							if (null != id) {
								query.setParameter(i++, id);
							}
						}
					}
					return query.list();
				}
			});
		if(null == list){
			list = new ArrayList();
		}
		return list;
	}

	public  List getEntityList(Object account_id,final Class cls, final SqlInfo sqlInfo,int strategy) throws DaoException {
		List list = (List) hibernateTemplate.execute(new HibernateCallback() {
			public Object doInHibernate(Session session)throws HibernateException, SQLException {
				    SQLQuery  query = session.createSQLQuery(sqlInfo.getSql()).addEntity(cls);
					if (null != sqlInfo.getParams() && sqlInfo.getParams().length > 0) {
						int i = 0;
						for (Object id : sqlInfo.getParams()) {
							if (null != id) {
								query.setParameter(i++, id);
							}
						}
					}
					return query.list();
				}
		    });
		if(null == list){
			list = new ArrayList();
		}
		return list;
	}

	private void processException(Exception e) throws DaoException {
		if (e instanceof MappingException) {
			throw new DaoException(DaoException.POJO_NOTFOUND_EXCEPTION, e);
		} 
		else if (e instanceof NullPointerException) {
			throw new DaoException(DaoException.NULLPOINTER_EXCEPTION, e);
		} 
		else if (e instanceof SQLException) {
			throw new DaoException(DaoException.SQL_EXCEPTION, e);
		} 
		else if (e instanceof HibernateException) {
			throw new DaoException(DaoException.Hibernate_Exception, e);
		} 
		else if (e instanceof DaoException) {
			throw (DaoException) e;
		} 
		else {
			throw new DaoException(e);
		}
	}

}
