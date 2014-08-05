package com.jnt.tree.core;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by arthur on 14-7-31.
 * <p/>
 * 用户的技能树，每个用户可以拥有多个技能树
 * <p/>
 * CREATE TABLE `user_tree` (
 * `id` bigint(20) NOT NULL,
 * `user_id` bigint(20) NOT NULL,
 * `tree_id` bigint(20) NOT NULL,
 * `create_at` bigint(20) DEFAULT NULL,
 * PRIMARY KEY (`id`)
 * ) ENGINE=InnoDB
 */

@Entity
@Table(name = "user_tree")
public class UserTree implements Serializable {

    private static final long serialVersionUID = -7203343128718524230L;

    private Long id;

    private Long userId;

    private Long treeId;

    private Long createAt;

    public UserTree() {
    }


    public UserTree(Long userId, Long treeId) {
        this.userId = userId;
        this.treeId = treeId;
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

    @Column(name = "user_id")
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Column(name = "tree_id")
    public Long getTreeId() {
        return treeId;
    }

    public void setTreeId(Long treeId) {
        this.treeId = treeId;
    }

    @Column(name = "create_at")
    public Long getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Long createAt) {
        this.createAt = createAt;
    }

    @Override
    public String toString() {
        return "UserTree{" +
                "id=" + id +
                ", userId=" + userId +
                ", treeId=" + treeId +
                '}';
    }

}
