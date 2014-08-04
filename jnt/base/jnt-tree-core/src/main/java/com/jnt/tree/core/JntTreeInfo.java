package com.jnt.tree.core;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by arthur on 14-7-31.
 */

/**
 * 具体的一棵树的信息
 */

@Entity
@Table(name = "tree_info")
public class JntTreeInfo implements Serializable {


    private static final long serialVersionUID = -7203343128718524236L;

    private Long id;         //无意义主键

    private Long treeId;     //每棵树有一个唯一的标示

    private Long nodeId;     //树上每个节点的唯一标示

    private String nodeName;  //每个节点对外展示的名字

    private Long parentId;   //树上每个节点的父亲节点标示



    public JntTreeInfo() {

    }


    public JntTreeInfo(Long treeId, Long nodeId, Long parentId) {
        this.treeId = treeId;
        this.nodeId = nodeId;
        this.parentId = parentId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "tree_id")
    public Long getTreeId() {
        return treeId;
    }

    public void setTreeId(Long treeId) {
        this.treeId = treeId;
    }

    @Column(name = "node_id")
    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    @Column(name = "parent_id")
    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    @Transient
    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }


    @Override
    public String toString() {
        return "JntTreeInfo{" +
                "id=" + id +
                ", treeId=" + treeId +
                ", nodeId=" + nodeId +
                ", parentId=" + parentId +
                '}';
    }


}
