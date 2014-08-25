package com.jnt.tree.core;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 具体的一棵树的信息
 * CREATE TABLE `tree_info` (
 * `id` bigint(20) NOT NULL,
 * `tree_id` bigint(20) NOT NULL,
 * `node_id` bigint(20) NOT NULL,
 * `parent_id` bigint(20) NOT NULL,
 * `create_at` bigint(20) NOT NULL,
 * PRIMARY KEY (`id`)
 * ) ENGINE=InnoDB
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

    private Long createAt;  //创建时间

    private List<JntTreeInfo> children;


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


    @Column(name = "create_at")
    public Long getCreateAt() {
        return createAt;
    }


    public void setCreateAt(Long createAt) {
        this.createAt = createAt;
    }

    @Transient
    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }


    @Transient
    public List<JntTreeInfo> getChildren() {
        return children;
    }

    public void setChildren(List<JntTreeInfo> children) {
        this.children = children;
    }

    public void setChild(JntTreeInfo child){
        if(null == children){
            children = new ArrayList<JntTreeInfo>();
        }
        children.add(child);
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
