package com.jnt.tree.web.util;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jnt.tree.core.JntTree;

/**
 * Created by arthur on 14-8-25.
 */
public class test {

    public static void main(String[] args){
        try
        {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
            JntTree jntTree = new JntTree();
            String str = objectMapper.writeValueAsString(jntTree);
            System.out.println(str);

        }
        catch (Exception e)
        {

        }
    }
}
