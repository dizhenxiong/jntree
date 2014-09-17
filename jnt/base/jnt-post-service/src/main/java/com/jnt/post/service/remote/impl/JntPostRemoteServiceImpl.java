package com.jnt.post.service.remote.impl;

import com.jnt.post.core.JntPost;
import com.jnt.post.service.JntPostService;
import com.jnt.post.service.remote.JntPostRemoteService;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by arthur on 14-9-17.
 */
public class JntPostRemoteServiceImpl implements JntPostRemoteService {


    private JntPostService<JntPost> jntPostJntPostService;


    @Resource(name = "jntPostService")
    public void setJntPostJntPostService(JntPostService<JntPost> jntPostJntPostService) {
        this.jntPostJntPostService = jntPostJntPostService;
    }

    @Override
    public Long save(JntPost jntPost) throws Exception {
        return jntPostJntPostService.saveEntity(jntPost);
    }

    @Override
    public boolean update(JntPost jntPost) throws Exception {
        return jntPostJntPostService.updateEntity(jntPost);
    }

    @Override
    public boolean delete(JntPost jntPost) throws Exception {
        return jntPostJntPostService.removeEntity(jntPost.getId());
    }

    @Override
    public List<JntPost> getPostListByLevel(Integer level) throws Exception {
        return null;
    }

    @Override
    public List<JntPost> getAllPostList() throws Exception {
        return null;
    }

    @Override
    public List<JntPost> getPostListByParentId(Long parentId, int start, int count) throws Exception {
        return jntPostJntPostService.getListByParentId(parentId, start, count);
    }
}
