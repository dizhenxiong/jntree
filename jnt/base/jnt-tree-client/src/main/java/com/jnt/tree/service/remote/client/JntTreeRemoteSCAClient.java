package com.jnt.tree.service.remote.client;

import com.jnt.tree.core.JntTree;
import com.jnt.tree.service.remote.JntTreeRemoteService;
import org.oasisopen.sca.annotation.Reference;

/**
 * Created with IntelliJ IDEA.
 * User: kangyang1
 * Date: 14-8-5
 * Time: 下午7:44
 * To change this template use File | Settings | File Templates.
 */
public class JntTreeRemoteSCAClient implements JntTreeRemoteService {

    private JntTreeRemoteService jntTreeRemoteService;

    @Reference
    public void setJntTreeRemoteService(JntTreeRemoteService jntTreeRemoteService) {
        this.jntTreeRemoteService = jntTreeRemoteService;
    }

    @Override
    public JntTree getJntTree(Long id) throws Exception {
        return jntTreeRemoteService.getJntTree(id);
    }
}
