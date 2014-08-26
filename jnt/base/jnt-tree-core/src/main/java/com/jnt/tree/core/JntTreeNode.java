package com.jnt.tree.core;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by arthur on 14-7-31.
 * <p/>
 * 树上的某个节点的信息
 *
 CREATE TABLE `tree_node` (
 `id` bigint(20) NOT NULL,
 `name` varchar(30) NOT NULL DEFAULT '',
 `create_at` bigint(20) NOT NULL,
 PRIMARY KEY (`id`)
 ) ENGINE=InnoDB
 */

@Entity
@Table(name = "tree_node")
@JsonIgnoreProperties({"id","createAt"})
public class JntTreeNode implements Serializable {

    private static final long serialVersionUID = -7203343128718524230L;

    private Long id;

    private String text;

    private Long createAt;

    public JntTreeNode() {

    }


    public JntTreeNode(String text) {
        this.text = text;
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
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
        return "JntTreeNode{" +
                "id=" + id +
                ", text='" + text + '\'' +
                '}';
    }

}
