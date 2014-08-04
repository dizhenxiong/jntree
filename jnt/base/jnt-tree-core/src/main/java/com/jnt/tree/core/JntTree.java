package com.jnt.tree.core;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by arthur on 14-8-1.
 */
@Entity
@Table(name = "jnt_tree")
public class JntTree implements Serializable {

    private static final long serialVersionUID = -7203343128718524234L;

    private Long id;           //主键

    private String name;       //树的名字

    private JntTreeInfo jntTreeInfo;  //树的详细信息

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

    @Transient
    public JntTreeInfo getJntTreeInfo() {
        return jntTreeInfo;
    }

    public void setJntTreeInfo(JntTreeInfo jntTreeInfo) {
        this.jntTreeInfo = jntTreeInfo;
    }


}
