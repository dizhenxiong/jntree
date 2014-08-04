package com.jnt.tree.service.impl;

import com.jnt.tree.core.JntTreeInfo;
import com.jnt.tree.service.JntTreeInfoService;
import com.jnt.tree.service.base.impl.DalBaseServiceImpl;
import org.springframework.stereotype.Component;

/**
 * Created by arthur on 14-8-1.
 */
@Component("jntTreeInfoService")
public class JntTreeInfoServiceImpl extends DalBaseServiceImpl<JntTreeInfo> implements JntTreeInfoService<JntTreeInfo> {

    @Override
    public Class<JntTreeInfo> getEntityClass() {
        return  JntTreeInfo.class;
    }



}
