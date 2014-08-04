package com.lenovo.vctl.apps.image.upload.util;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.lenovo.vctl.apps.image.JsonCallback;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;


public class JsonCallBackTemplate {

    protected Logger log = Logger.getLogger(this.getClass());
    private JsonCallback callback;

    public void setCallback(JsonCallback callback) {
        this.callback = callback;
    }

    public void sendJson(HttpServletResponse response) {
        JsonFactory factory = new JsonFactory();
        JsonGenerator generator;
        try {
            generator = factory.createJsonGenerator(response.getWriter());
            generator.setCodec(new ObjectMapper());
            generator.writeStartObject();
            callback.setProperties(generator);
            generator.writeEndObject();
            generator.close();
        } catch (IOException e) {
//			e.printStackTrace();
            log.error(e.getMessage(), e);
        }

    }

}
