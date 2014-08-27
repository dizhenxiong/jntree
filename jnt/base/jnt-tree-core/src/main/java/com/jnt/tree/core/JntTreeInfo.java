package com.jnt.tree.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
 *

 {"data ":{"text ":"a 'a","expandState":"expand"},
 "children":[
  {
 "data":          {"text":"bb","expandState":"expand"},
 "children":[{"data":{"text":"rr"}},{"data":{"text":"nnn"}
  }
 ]
 },

 */


@Entity
@Table(name = "tree_info")
@JsonIgnoreProperties({"createAt","parentId"})
public class JntTreeInfo implements Serializable {


    private static final long serialVersionUID = -7203343128718524236L;

    @JsonIgnore
    private Long id;         //无意义主键

    @JsonIgnore
    private Long treeId;     //每棵树有一个唯一的标示

    @JsonIgnore
    private Long nodeId;     //树上每个节点的唯一标示


    private JntTreeNode data;  //每个节点对外展示的名字

    private Long parentId;   //树上每个节点的父亲节点标示

    private Long createAt;  //创建时间

    private List<JntTreeInfo> children = null;


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
    public List<JntTreeInfo> getChildren() {
        return children;
    }

    public void setChildren(List<JntTreeInfo> children) {
        this.children = children;
    }

    @Transient
    public JntTreeNode getData() {
        return data;
    }

    public void setData(JntTreeNode data) {
        this.data = data;
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
