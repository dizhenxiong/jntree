package com.lenovo.vctl.apps.service;

import com.lenovo.vctl.apps.model.Material;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kangyang1
 * Date: 14-5-15
 * Time: 下午4:40
 * To change this template use File | Settings | File Templates.
 */
public interface MasterialService<Material>  extends DalBaseService<Material> {

    public List<Material> getUserMaterialList(Long userId) throws Exception;

    public List<Material> getAllMaterialList() throws Exception;
}