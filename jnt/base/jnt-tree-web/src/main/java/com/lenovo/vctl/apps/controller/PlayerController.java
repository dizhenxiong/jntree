package com.lenovo.vctl.apps.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jnt.tree.core.JntTree;
import com.jnt.tree.service.remote.JntTreeRemoteService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PlayerController {
    private static final Log log = LogFactory.getLog(PlayerController.class);
    @Autowired
    public JntTreeRemoteService jntTreeRemoteService;

    /**
     * 玩家登入
     *
     * @param request
     * @param response
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/memory/tree")
         public String tree(HttpServletRequest request, HttpServletResponse response, ModelMap model, String name,
                             String password) throws Exception {
        JntTree jntTree = jntTreeRemoteService.getJntTree(1l);
        model.put("tree", jntTree);
        return "simple1";
    }

    @RequestMapping(value = "/memory/index")
    public String index(HttpServletRequest request, HttpServletResponse response, ModelMap model, String name,
                        String password) throws Exception {
        return "simple1";
    }

    public void setJntTreeRemoteService(JntTreeRemoteService jntTreeRemoteService) {
        this.jntTreeRemoteService = jntTreeRemoteService;
    }


}
