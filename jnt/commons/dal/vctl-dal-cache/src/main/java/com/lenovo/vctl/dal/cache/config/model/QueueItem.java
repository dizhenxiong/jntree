package com.lenovo.vctl.dal.cache.config.model;

public class QueueItem {
    private String datasource = null;
    private String name = null;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDatasource() {
        return datasource;
    }

    public void setDatasource(String datasource) {
        this.datasource = datasource;
    }

    /**
         * 
         * @return 
         * @author 
         */
        public String toString() {
            StringBuffer buffer = new StringBuffer();
            buffer.append("QueueItem[");
            buffer.append("datasource = ").append(datasource);
            buffer.append(",\n name = ").append(name);
            buffer.append("]");
            return buffer.toString();
        }
    
}
