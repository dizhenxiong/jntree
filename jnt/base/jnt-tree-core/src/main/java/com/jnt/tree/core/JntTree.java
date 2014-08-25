package com.jnt.tree.core;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by arthur on 14-8-1.
 * CREATE TABLE `jnt_tree` (
 * `id` bigint(20) NOT NULL,
 * `name` varchar(30) NOT NULL DEFAULT '',
 * `create_at` bigint(20) NOT NULL,
 * PRIMARY KEY (`id`)
 * ) ENGINE=InnoDB
 */
@Entity
@Table(name = "jnt_tree")
public class JntTree implements Serializable {

    private static final long serialVersionUID = -7203343128718524234L;

    private Long id;           //主键

    private String name;       //树的名字

    private Long headNode;   //树的最上面的node id,是所有node 的祖先

    private Long createAt;

    public JntTree() {

    }

    public JntTree(String sName) {
        this.name = sName;
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

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Column(name = "head_node")
    public Long getHeadNode() {
        return headNode;
    }

    public void setHeadNode(Long headNode) {
        this.headNode = headNode;
    }

    @Column(name = "create_at")
    public Long getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Long createAt) {
        this.createAt = createAt;
    }

}
