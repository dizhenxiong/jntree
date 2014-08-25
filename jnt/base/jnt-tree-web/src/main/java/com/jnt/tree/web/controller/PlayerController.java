package com.jnt.tree.web.controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jnt.tree.consts.DalConstants;
import com.jnt.tree.core.JntTree;
import com.jnt.tree.core.JntTreeDTO;
import com.jnt.tree.core.JntTreeInfo;
import com.jnt.tree.web.util.EmptySerializer;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.ser.StdSerializerProvider;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import com.jnt.tree.service.remote.JntTreeRemoteService;
import org.springframework.beans.factory.annotation.Autowired;
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
        JntTreeDTO jntTree = jntTreeRemoteService.getJntTree(1l);
        return writeConent(response.getWriter(),jntTree) ;
    }


//    @RequestMapping(value = "/memory/index")
//    public String index(HttpServletRequest request, HttpServletResponse response, ModelMap model, String name,
//                        String password) throws Exception {
//        JntTreeDTO jntTree = jntTreeRemoteService.getJntTree(1l);
//        log.info("tree");
//        return "result";
//    }


    public String writeConent(PrintWriter writer, JntTreeDTO jntTree) {
        JsonGenerator generator = null;
        // First: need a custom serializer provider
        StdSerializerProvider sp = new StdSerializerProvider();
        sp.setNullValueSerializer(new EmptySerializer());
        try {
            JsonFactory factory = new JsonFactory();
            generator = factory.createJsonGenerator(writer);
            ObjectMapper objmap = new ObjectMapper();
            objmap.setSerializerProvider(sp);
            SerializationConfig serializationConfig = objmap.getSerializationConfig();
            generator.setCodec(objmap);
            generator.writeStartObject();

            generator.writeStringField("expandState", "expand");
            generator.writeStringField("template", null);
            generator.writeStringField("theme", "fresh-blue");
            generator.writeStringField("version", "1.2.1");
            //1.遍历整块树递归生成
            writeJnt(generator,jntTree.getBaseJntTreeInfo());
            //1.遍历整块树递归生成
            generator.writeEndObject();
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
                generator.writeStringField("text", jntTreeInfo.getNodeName());
                generator.writeEndObject();
//            }
//            if (CollectionUtils.isNotEmpty(jntTreeInfo.getChildren())) {
//                generator.writeArrayFieldStart("children");
//                for (JntTreeInfo jInfo : jntTreeInfo.getChildren()) {
//                    writeJnt(generator, jInfo);
//                }
//                generator.writeEndArray();
//            }
//            if (!jntTreeInfo.getParentId().equals(DalConstants.rootParentId)) {
//                generator.writeEndObject();
//            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }

    }


}
