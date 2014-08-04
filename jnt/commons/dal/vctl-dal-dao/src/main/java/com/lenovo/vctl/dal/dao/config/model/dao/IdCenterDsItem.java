package com.lenovo.vctl.dal.dao.config.model.dao;

import java.io.Serializable;

public class IdCenterDsItem implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -5587314855408495330L;
    private String ds;

    public String getDs() {
        return ds;
    }

    public void setDs(String ds) {
        this.ds = ds;
    }
    
    /**
      * @return 
      * @author 
      */
        public String toString() {
            StringBuffer buffer = new StringBuffer();
            buffer.append("IdCenterDsItem[");
            buffer.append("ds = ").append(ds);
            buffer.append("]");
            return buffer.toString();
        }
    
}
