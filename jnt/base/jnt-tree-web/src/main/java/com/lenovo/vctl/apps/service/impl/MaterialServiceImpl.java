package com.lenovo.vctl.apps.service.impl;

import com.lenovo.vctl.apps.constants.DalConstants;
import com.lenovo.vctl.apps.model.Material;
import com.lenovo.vctl.apps.service.MasterialService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: kangyang1
 * Date: 14-5-15
 * Time: 下午5:13
 * To change this template use File | Settings | File Templates.
 */
@Component("materialservice" )
public class MaterialServiceImpl extends DalBaseServiceImpl<Material> implements MasterialService<Material> {
    @Override
    public Class<Material> getEntityClass() {
        return Material.class;
    }

    @Override
    public List<Material> getUserMaterialList(Long userId) throws Exception {
        List<Long> idLs = getIdList(DalConstants.USER_MATERIAL_ID_LIST, new Object[]{userId}, 0, 100000, true);
        if (CollectionUtils.isEmpty(idLs)) return new ArrayList<Material>();

        return getObjectList(userId, idLs);
    }

    @Override
    public List<Material> getAllMaterialList() throws Exception {
        List<Long> idLs = getIdList(DalConstants.USER_ALLMATERIAL_ID_LIST, new Object[]{1}, 0, 100000, true);
        if (CollectionUtils.isEmpty(idLs)) return new ArrayList<Material>();

        return getObjectList(idLs);
    }

}
