package com.jnt.tree.service.impl;

import com.jnt.tree.core.UserTree;
import com.jnt.tree.service.UserTreeService;
import com.jnt.tree.service.base.impl.DalBaseServiceImpl;
import org.springframework.stereotype.Component;

/**
 * Created by arthur on 14-8-1.
 */

@Component("userTreeService")
public class UserTreeServiceImpl extends DalBaseServiceImpl<UserTree> implements UserTreeService<UserTree> {
    @Override
    public Class<UserTree> getEntityClass() {
        return UserTree.class;
    }

}
