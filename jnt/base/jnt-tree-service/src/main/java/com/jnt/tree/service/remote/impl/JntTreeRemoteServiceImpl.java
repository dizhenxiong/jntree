package com.jnt.tree.service.remote.impl;

import com.jnt.tree.core.JntTree;
import com.jnt.tree.core.JntTreeInfo;
import com.jnt.tree.core.JntTreeNode;
import com.jnt.tree.service.JntTreeInfoService;
import com.jnt.tree.service.JntTreeNodeService;
import com.jnt.tree.service.JntTreeService;
import com.jnt.tree.service.remote.JntTreeRemoteService;
import org.apache.commons.collections.CollectionUtils;
import org.oasisopen.sca.annotation.Service;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: kangyang1
 * Date: 14-8-4
 * Time: 下午4:10
 * To change this template use File | Settings | File Templates.
 */
@Service(JntTreeRemoteService.class)
@Component("JntTreeRemoteService")
public class JntTreeRemoteServiceImpl implements JntTreeRemoteService {

    private JntTreeService<JntTree> jntTreeService;
    private JntTreeInfoService<JntTreeInfo> jntTreeInfoService;
    private JntTreeNodeService<JntTreeNode> jntTreeNodeService;

    @Resource(name = "jntTreeService")
    public void setJntTreeService(JntTreeService jntTreeService) {
        this.jntTreeService = jntTreeService;
    }

    @Resource(name = "jntTreeInfoService")
    public void setJntTreeInfoService(JntTreeInfoService jntTreeInfoService) {
        this.jntTreeInfoService = jntTreeInfoService;
    }

    @Resource(name = "jntTreeNodeService")
    public void setJntTreeNodeService(JntTreeNodeService jntTreeNodeService) {
        this.jntTreeNodeService = jntTreeNodeService;
    }

    @Override
    public JntTree getJntTree(Long id) {
        JntTree jntTree = null;
        try {
            jntTree = (JntTree) jntTreeService.getEntity(id);
            if (null != jntTree) {
                List<JntTreeInfo> jntTreeInfos = jntTreeInfoService.getJntTreeInfoByTreeId(id);
                //装配TreeInfo
                Set<Long> nodeIdSet = new HashSet<Long>();
                Map<Long, String> nodeMap = new HashMap<Long, String>();
                for (JntTreeInfo jntTreeInfo : jntTreeInfos) {
                    nodeIdSet.add(jntTreeInfo.getNodeId());
                    nodeIdSet.add(jntTreeInfo.getParentId());
                }
                if (CollectionUtils.isNotEmpty(nodeIdSet)) {
                    List<Long> nodeIdLs = new ArrayList<Long>();
                    nodeIdLs.addAll(nodeIdSet);
                    List<JntTreeNode> jntTreeNodes = jntTreeNodeService.getObjectList(nodeIdLs);
                    for (JntTreeNode jntTreeNode : jntTreeNodes) {
                        nodeMap.put(jntTreeNode.getId(), jntTreeNode.getName());
                    }
                }
                //具体装配JntTreeInfo
                for (JntTreeInfo jntTreeInfo : jntTreeInfos) {
                    jntTreeInfo.setNodeName(nodeMap.get(jntTreeInfo.getNodeId()));
                }
                jntTree.setJntTreeInfoLs(jntTreeInfos);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
