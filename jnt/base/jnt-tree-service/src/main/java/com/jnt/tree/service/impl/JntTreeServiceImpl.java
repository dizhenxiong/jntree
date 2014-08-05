package com.jnt.tree.service.impl;

import com.jnt.tree.core.JntTree;
import com.jnt.tree.service.JntTreeService;
import com.jnt.tree.service.base.impl.DalBaseServiceImpl;
import org.springframework.stereotype.Component;

/**
 * Created by arthur on 14-8-1.
 */
@Component("jntTreeService")
public class JntTreeServiceImpl extends DalBaseServiceImpl<JntTree> implements JntTreeService<JntTree> {
    @Override
    public Class<JntTree> getEntityClass() {
        return JntTree.class;
    }


}
