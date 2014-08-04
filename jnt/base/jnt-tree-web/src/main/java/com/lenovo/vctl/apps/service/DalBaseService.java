package com.lenovo.vctl.apps.service;


import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kangyang1
 * Date: 12-8-16
 * Time: 下午9:22
 * To change this template use File | Settings | File Templates.
 */
public interface DalBaseService<E> {

    public Long saveEntity(E entity) throws Exception;

    public Long saveEntity(Long userId, E entity) throws Exception;

    public List<E> batchSave(List<E> entity) throws Exception;

    public boolean updateEntity(E entity) throws Exception;

    public boolean updateEntity(Long userId, E entity) throws Exception;

    public boolean removeEntity(Long entityId) throws Exception;

    public boolean removeEntity(Long userId, Long entity) throws Exception;

    public boolean removeEntitys(List<Long> entityIds) throws Exception;

    public boolean deleteList(Object account_id, String list_name, Object[] params) throws Exception;

    public E getEntity(Long id) throws Exception;

    public E getEntity(Object userId, Serializable entityId) throws Exception ;

    public List<E> getObjectList(List ids) throws Exception;

    public List<E> getObjectList(Long userId, List ids) throws Exception;

    public Integer count(String regItem, Object[] params) throws Exception;

    public Integer count(Long userId, String regItem, Object[] params) throws Exception;

    public Object getMapping(Object account_id, String map_name, Object[] params) throws Exception;

    public List getMappings(Object accountId, String mapName, List<Object[]> paramsList) throws Exception;




}
