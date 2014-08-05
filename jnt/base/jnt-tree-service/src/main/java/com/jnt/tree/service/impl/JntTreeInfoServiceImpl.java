package com.jnt.tree.service.impl;

import com.jnt.tree.consts.DalConstants;
import com.jnt.tree.core.JntTreeInfo;
import com.jnt.tree.service.JntTreeInfoService;
import com.jnt.tree.service.base.impl.DalBaseServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arthur on 14-8-1.
 */
@Component("jntTreeInfoService")
public class JntTreeInfoServiceImpl extends DalBaseServiceImpl<JntTreeInfo> implements JntTreeInfoService<JntTreeInfo> {

    @Override
    public Class<JntTreeInfo> getEntityClass() {
        return JntTreeInfo.class;
    }

    @Override
    public List<JntTreeInfo> getJntTreeInfoByTreeId(Long treeId) throws Exception {
        List<JntTreeInfo> ls = new ArrayList<JntTreeInfo>();
        List<Long> idLs = getIdList(treeId, DalConstants.TREEINFO_TREEID_LIST, new Object[]{treeId}, 0, DalConstants.MAX_TREE_NODE, true);
        if (CollectionUtils.isNotEmpty(idLs)) {
            ls = getObjectList(treeId, idLs);
        }
        return ls;
    }
}
