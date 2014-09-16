package com.jnt.tree.core;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: kangyang1
 * Date: 14-8-25
 * Time: 下午5:18
 * To change this template use File | Settings | File Templates.
 */
public class JntTreeDTO implements Serializable {

    private static final long serialVersionUID = -7203343128718526734L;

    public JntTreeDTO() {

    }

    public JntTree jntTree;

    public JntTreeInfo baseJntTreeInfo;  //树的详细信息

    public JntTreeInfo getBaseJntTreeInfo() {
        return baseJntTreeInfo;
    }

    public void setBaseJntTreeInfo(JntTreeInfo baseJntTreeInfo) {
        this.baseJntTreeInfo = baseJntTreeInfo;
    }

    public JntTree getJntTree() {
        return jntTree;
    }

    public void setJntTree(JntTree jntTree) {
        this.jntTree = jntTree;
    }


}
