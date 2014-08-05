package com.jnt.tree.service.remote;

import com.jnt.tree.core.JntTree;
import org.oasisopen.sca.annotation.Remotable;

import java.util.List;

/**
 * Created by arthur on 14-8-1.
 */


@Remotable
public interface JntTreeRemoteService {

    public JntTree getJntTree(Long id) throws Exception;

}
