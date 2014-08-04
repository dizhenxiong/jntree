package com.lenovo.vctl.apps.image.upload.util;


import java.util.Map;

import com.lenovo.vctl.apps.image.JsonCallback;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerator;

public class JsonCallbackAdapter implements JsonCallback {

    private Logger log=Logger.getLogger(this.getClass());
    private Map map;

    public JsonCallbackAdapter(Map map){
        this.map = map;
    }

    public JsonCallbackAdapter() {
        super();
    }


    @Override
    public void setProperties(JsonGenerator generator) {

    }

    public Logger getLog(){
        return log;
    }


    public Map getMap() {
        return map;
    }


    public void setMap(Map map) {
        this.map = map;
    }


}
