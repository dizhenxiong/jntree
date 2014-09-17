package com.jnt.post.service.base.impl;

import com.jnt.post.service.base.DalBaseService;
import com.lenovo.vctl.dal.dao.Dao;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kangyang1
 * Date: 12-8-16
 * Time: 下午9:47
 * To change this template use File | Settings | File Templates.
 */
public abstract class DalBaseServiceImpl<E> implements DalBaseService<E> {

    private static Log log = LogFactory.getLog(DalBaseServiceImpl.class);

    private static final int singleUsedTime = 15;
    private static final int lotsUsedTime = 50;
    private Log permLog = LogFactory.getLog("DalBasePerformance");
    public Dao dao;

    public abstract Class<E> getEntityClass();

    public Dao getDao() {
        return dao;
    }

    @Autowired
    public void setDao(Dao dao) {
        this.dao = dao;
    }
    /*
      * @param userId
      * @param entityId
      * @return
      * @throws Exception
      */

    public E getEntity(Object userId, Serializable entityId) throws Exception {
        long t1 = System.currentTimeMillis();
        E value = null;
        try {
            value = (E) dao.get(userId, getEntityClass(), entityId);
        } catch (Exception e) {
            e.printStackTrace(System.err);
            throw new Exception(e);
        }

        long usedTime = System.currentTimeMillis() - t1;
        if (usedTime >= singleUsedTime) {
            if (permLog.isWarnEnabled()) {
                permLog.warn("method: get region: " + getEntityClass().getName() + " params: " + entityId + " time: " + usedTime);
            }
        }

        return value;
    }

    /*
      * @param userId
      * @param entityId
      * @return
      * @throws Exception
      */

    public E getEntity(Long entityId) throws Exception {
        long t1 = System.currentTimeMillis();
        E value = null;
        try {
            value = (E) dao.get(getEntityClass(), entityId);
        } catch (Exception e) {
            throw new Exception(e);
        }
        long usedTime = System.currentTimeMillis() - t1;
        if (usedTime >= singleUsedTime) {
            if (permLog.isWarnEnabled()) {
                permLog.warn("method: get region: " + getEntityClass().getName() + " params: " + entityId + " time: " + usedTime);
            }
        }
        return value;
    }


    protected boolean updateEntity(Long userId, Long entityId, E entity) throws Exception {
        long t1 = System.currentTimeMillis();
        boolean bRes = false;
        try {
            bRes = dao.update(userId, entity);
        } catch (Exception e) {
            throw new Exception(e);
        }
        long usedTime = System.currentTimeMillis() - t1;
        if (usedTime >= singleUsedTime) {
            if (permLog.isWarnEnabled()) {
                permLog.warn("method: update region: " + getEntityClass().getName() + " params: " + entityId + " time: " + usedTime);
            }
        }
        return bRes;
    }

    /*
      * @param userId
      * @param entityId
      * @param entity
      * @return
      * @throws Exception
      */

    public boolean updateEntity(E entity) throws Exception {
        boolean bRes = false;
        try {
            bRes = dao.update(entity);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            throw new Exception(e);
        }
        return bRes;
    }

    @Override
    public boolean updateEntity(Long userId, E entity) throws Exception {
        long t1 = System.currentTimeMillis();
        boolean bRes = false;
        try {
            bRes = dao.update(userId, entity);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            throw new Exception(e);
        }
        return bRes;
    }
    
    /*
      * @param userId
      * @param entityId
      * @param entity
      * @return
      * @throws Exception
      */

    public boolean removeEntity(Long entityId) throws Exception {
        long t1 = System.currentTimeMillis();
        boolean bRes = false;
        try {
            bRes = dao.delete(getEntityClass(), entityId);
        } catch (Exception e) {
            throw new Exception(e);
        }
        long usedTime = System.currentTimeMillis() - t1;
        if (usedTime >= singleUsedTime) {
            if (permLog.isWarnEnabled()) {
                permLog.warn("method: removeEntity region: " + getEntityClass().getName() + " params: " + entityId + " time: " + usedTime);
            }
        }
        return bRes;

    }

    public boolean removeEntitys(List<Long> entityIds) throws Exception {
        boolean bRes = false;
        try {
            bRes = dao.deleteList(getEntityClass(), entityIds);
        } catch (Exception e) {
            throw new Exception(e);
        }
        return bRes;
    }

    @Override
    public boolean deleteList(Object account_id, String list_name, Object[] params) throws Exception {
        return dao.deleteList(account_id, list_name, params);
    }

    /*
    * @param userId
    * @param entityId
    * @param entity
    * @return
    * @throws Exception
    */

    public boolean removeEntity(Long userId, Long entityId) throws Exception {
        long t1 = System.currentTimeMillis();
        boolean bRes = false;
        try {
            bRes = dao.delete(userId, getEntityClass(), entityId);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            throw new Exception(e);
        }
        return bRes;
    }

    /**
     * @param entity
     * @return
     * @throws Exception
     */
    public Long saveEntity(E entity) throws Exception {
        long t1 = System.currentTimeMillis();
        Long id = null;
        try {
            id = (Long) dao.save(entity);
        } catch (Exception e) {
            throw new Exception(e);
        }
        return id;

    }

    public List<E> batchSave(List<E> entities) throws Exception {
        long t1 = System.currentTimeMillis();
        List<E> objs = null;
        try {
            objs = (List<E>) dao.batchSave(entities);
        } catch (Exception e) {
            throw new Exception(e);
        }
        long usedTime = System.currentTimeMillis() - t1;
        if (usedTime >= lotsUsedTime) {
            if (permLog.isWarnEnabled()) {
                Object obj = entities.get(0);
                if (null != obj && null != obj.getClass()) {
                    permLog.warn("method: batchSave region: " + obj.getClass().getName() + " size: " + entities.size() + " time: " + usedTime);
                }
            }
        }
        return objs;
    }


    /**
     * @param userId
     * @param entity
     * @return
     * @throws Exception
     */
    public Long saveEntity(Long userId, E entity) throws Exception {
        long t1 = System.currentTimeMillis();
        Long id = null;
        try {
            id = (Long) dao.save(userId, entity);
        } catch (Exception e) {
            throw new Exception(e);
        }
        return id;
    }


    public List<E> getObjectList(List ids) throws Exception {
        long t1 = System.currentTimeMillis();
        List<E> objList = new ArrayList<E>();
        if (CollectionUtils.isEmpty(ids)) {
            return objList;
        }
        try {
            objList = dao.getList(getEntityClass(), ids);
        } catch (Exception e) {
            throw new Exception(e);
        }

        long usedTime = System.currentTimeMillis() - t1;
        if (usedTime >= lotsUsedTime) {
            if (permLog.isWarnEnabled()) {
                permLog.warn("method: getObjectList region: " + getEntityClass().getName() + " size: " + ids.size() + " time: " + usedTime);
            }
        }
        return objList;
    }

    public List<E> getObjectList(Long userId, List ids) throws Exception {
        long t1 = System.currentTimeMillis();
        List<E> objList = new ArrayList<E>();
        if (null == userId || CollectionUtils.isEmpty(ids)) {
            return objList;
        }
        try {
            objList = dao.getList(userId, getEntityClass(), ids);
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        long usedTime = System.currentTimeMillis() - t1;
        if (usedTime >= lotsUsedTime) {
            if (permLog.isWarnEnabled()) {
                permLog.warn("method: getObjectList region: " + getEntityClass().getName() + " size: " + ids.size() + " time: " + usedTime);
            }
        }
        return objList;
    }

    public List getIdList(Object account_id, String list_name, Object[] params, Integer start, Integer count, boolean forward) throws Exception {
        long t1 = System.currentTimeMillis();
        List idList = new ArrayList();
        try {
            idList = dao.getIdList(account_id, list_name, params, start, count);
        } catch (Exception e) {
            throw new Exception(e);
        }
        long usedTime = System.currentTimeMillis() - t1;
        if (usedTime >= singleUsedTime) {
            if (permLog.isWarnEnabled()) {
                 permLog.warn("method: getIdList region: " + list_name + " params: " +params.toString()+ "&" + start + "&" + count + " time: " + usedTime);
            }
        }
        return idList;
    }


    public List getIdList(String list_name, Object[] params, Integer start, Integer count, boolean forward) throws Exception {
        long t1 = System.currentTimeMillis();
        List idList = new ArrayList();
        try {
            idList = dao.getIdList(list_name, params, start, count);
        } catch (Exception e) {
            throw new Exception(e);
        }
        long usedTime = System.currentTimeMillis() - t1;
        if (usedTime >= singleUsedTime) {
            if (permLog.isWarnEnabled()) {
                permLog.warn("method: getIdList region: " + list_name + " params: " +params.toString()+ "&" + start + "&" + count + " time: " + usedTime);
            }
        }
        return idList;
    }


    public Object getMapping(Object account_id, String map_name, Object[] params) throws Exception {
        long t1 = System.currentTimeMillis();
        Object obj = null;
        try {
            obj = dao.getMapping(account_id, map_name, params);

        } catch (Exception e) {
            throw new Exception(e);
        }
        return obj;
    }

    public Object getMapping(String map_name, Object[] params) throws Exception {
        Object obj = null;
        try {
            obj = dao.getMapping(map_name, params);
        } catch (Exception e) {
            throw new Exception(e);
        }
        return obj;
    }

    public List getMappings(Object accountId, String mapName, List<Object[]> paramsList) throws Exception{
        List ls = new ArrayList();
        try{
            ls = dao.getMappings(accountId,mapName,paramsList);
        } catch(Exception e){
            throw new Exception(e);
        }
        return ls;
    }


    /**
     * @param userId
     * @param regItem
     * @param params
     * @return
     * @throws Exception
     */
    public Integer count(Object userId, String regItem, Object[] params) throws Exception {
        long t1 = System.currentTimeMillis();
        Integer result = null;
        try {
            result = dao.count(userId, regItem, params);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            throw new Exception(e);
        }
        result = result == null ? Integer.valueOf(0) : result;
        return result;
    }

    public Integer count(String regItem, Object[] params) throws Exception {
        long t1 = System.currentTimeMillis();
        Integer result = null;
        try {
            result = dao.count(regItem, params);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            throw new Exception(e);
        }
        result = result == null ? Integer.valueOf(0) : result;
        return result;
    }

    @Override
    public Integer count(Long userId, String regItem, Object[] params) throws Exception {
        long t1 = System.currentTimeMillis();
        Integer result = null;
        try {
            result = dao.count(userId, regItem, params);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            throw new Exception(e);
        }
        result = result == null ? Integer.valueOf(0) : result;
        return result;
    }


}
