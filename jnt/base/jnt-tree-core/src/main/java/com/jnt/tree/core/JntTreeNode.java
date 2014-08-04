package com.jnt.tree.core;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by arthur on 14-7-31.
 * <p/>
 * 树上的某个节点的信息
 */

@Entity
@Table(name = "node")
public class JntTreeNode implements Serializable {

    private static final long serialVersionUID = -7203343128718524230L;

    private Long id;

    private String name;

    public JntTreeNode() {

    }


    public JntTreeNode(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "JntTreeNode{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}
