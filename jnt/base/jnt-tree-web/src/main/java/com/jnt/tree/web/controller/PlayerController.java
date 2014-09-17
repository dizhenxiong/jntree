package com.jnt.tree.web.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.google.gson.Gson;
import com.jnt.tree.core.JntTreeDTO;
import com.jnt.tree.core.JntTreeInfo;
import com.jnt.tree.service.remote.JntTreeRemoteService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class PlayerController {
    private static final Log log = LogFactory.getLog(PlayerController.class);


    @Autowired
    public JntTreeRemoteService jntTreeRemoteService;

//    public void setJntTreeRemoteService(JntTreeRemoteService jntTreeRemoteService) {
//        this.jntTreeRemoteService = jntTreeRemoteService;
//    }


    @RequestMapping(method = RequestMethod.GET, value = "/player/tree/{id}")
    public String tree(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        JntTreeDTO jntTree = jntTreeRemoteService.getJntTree(id);
        return writeConent(response.getWriter(), jntTree);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/player/show/{id}")
    public String show(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response,ModelMap modal) throws Exception {
        modal.addAttribute("id",id);
        return "tree";
    }

//    @RequestMapping(value = "/memory/index")
//    public String index(HttpServletRequest request, HttpServletResponse response, ModelMap model, String name,
//                        String password) throws Exception {
//        JntTreeDTO jntTree = jntTreeRemoteService.getJntTree(1l);
//        log.info("tree");
//        return "result";
//    }




    public String writeConent(PrintWriter writer, JntTreeDTO jntTree) {
        Gson gson = new Gson();
        JsonGenerator generator = null;
        // First: need a custom serializer provider
//        StdSerializerProvider sp = new StdSerializerProvider();
//        sp.setNullValueSerializer(new EmptySerializer());
        try {
            JsonFactory factory = new JsonFactory();
            generator = factory.createGenerator(writer);
            ObjectMapper objmap = new ObjectMapper();
            objmap.setSerializationInclusion(JsonInclude.Include.NON_NULL);
//            objmap.setSerializerProvider(sp);
            SerializationConfig serializationConfig = objmap.getSerializationConfig();
            generator.setCodec(objmap);
            generator.writeObject(jntTree.getBaseJntTreeInfo());
//            generator.writeStartObject();
//
//            generator.writeStringField("expandState", "expand");
//            generator.writeStringField("template", null);
//            generator.writeStringField("theme", "fresh-blue");
//            generator.writeStringField("version", "1.2.1");
//            //1.遍历整块树递归生成
//            writeJnt(generator,jntTree.getBaseJntTreeInfo());
//            //1.遍历整块树递归生成
//            generator.writeEndObject();
            generator.close();
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    public void writeJnt(JsonGenerator generator, JntTreeInfo jntTreeInfo) {
        try {
//            if (!jntTreeInfo.getParentId().equals(DalConstants.rootParentId)) {
            generator.writeObjectFieldStart("data");
            generator.writeEndObject();
//            }
            if (CollectionUtils.isNotEmpty(jntTreeInfo.getChildren())) {
                generator.writeArrayFieldStart("children");
                for (JntTreeInfo jInfo : jntTreeInfo.getChildren()) {
                    writeJnt(generator, jInfo);
                }
                generator.writeEndArray();
            }
//            if (!jntTreeInfo.getParentId().equals(DalConstants.rootParentId)) {
//                generator.writeEndObject();
//            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }

    }


}
