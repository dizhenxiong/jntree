package com.jnt.tree.service.impl;

import com.jnt.tree.core.PostTree;
import com.jnt.tree.service.PostTreeService;
import com.jnt.tree.service.base.impl.DalBaseServiceImpl;
import org.springframework.stereotype.Component;

/**
 * Created by arthur on 14-8-1.
 */

@Component("postTreeService")

public class PostTreeServiceImpl extends DalBaseServiceImpl<PostTree> implements PostTreeService<PostTree> {
    @Override
    public Class<PostTree> getEntityClass() {
        return  PostTree.class;
    }
}
