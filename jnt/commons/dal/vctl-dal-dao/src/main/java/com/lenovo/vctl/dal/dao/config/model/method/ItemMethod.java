package com.lenovo.vctl.dal.dao.config.model.method;

import java.io.Serializable;
import java.lang.reflect.Method;

public interface ItemMethod extends Serializable {
    public Method[] getKeyMethod();
}
