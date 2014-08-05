package com.jnt.tree.service.impl;

import com.jnt.tree.core.JntTreeNode;
import com.jnt.tree.service.JntTreeNodeService;
import com.jnt.tree.service.base.impl.DalBaseServiceImpl;
import org.springframework.stereotype.Component;

/**
 * Created by arthur on 14-8-1.
 */

@Component("jntTreeNodeService")
public class JntTreeNodeServiceImpl extends DalBaseServiceImpl<JntTreeNode> implements JntTreeNodeService<JntTreeNode> {

    @Override
    public Class<JntTreeNode> getEntityClass() {
        return  JntTreeNode.class;
    }

}
