package com.jnt.tree.service;

import com.jnt.tree.service.base.DalBaseService;

import java.util.List;

/**
 * Created by arthur on 14-8-1.
 */
public interface JntTreeInfoService<JntTreeInfo> extends DalBaseService<JntTreeInfo> {

    public List<JntTreeInfo> getJntTreeInfoByTreeId(Long treeId) throws Exception;
}
