package com.lenovo.vctl.apps.image;

import org.codehaus.jackson.JsonGenerator;

public interface JsonCallback {
    public void setProperties(JsonGenerator generator);
}