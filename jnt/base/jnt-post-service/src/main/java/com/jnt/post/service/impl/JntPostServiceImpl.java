package com.jnt.post.service.impl;

import com.jnt.post.consts.PostConstants;
import com.jnt.post.core.JntPost;
import com.jnt.post.service.JntPostService;
import com.jnt.post.service.base.impl.DalBaseServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arthur on 14-9-17.
 */

@Component("jntPostService")

public class JntPostServiceImpl extends DalBaseServiceImpl<JntPost> implements JntPostService<JntPost> {

    @Override
    public Class<JntPost> getEntityClass() {
        return JntPost.class;
    }

    @Override
    public List<JntPost> getListByParentId(Long parentId, int start, int count) throws Exception {

        List<JntPost> jntPosts = new ArrayList<JntPost>();

        List<Long> idLs = getIdList(PostConstants.POST_ID_LIST_PARENT, new Object[]{parentId}, start, count, true);

        if (CollectionUtils.isNotEmpty(idLs)) {
            jntPosts = getObjectList(idLs);
        }
        return null;
    }


}
