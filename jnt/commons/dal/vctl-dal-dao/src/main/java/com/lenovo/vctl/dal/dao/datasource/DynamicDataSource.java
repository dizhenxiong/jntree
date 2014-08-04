package com.lenovo.vctl.dal.dao.datasource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.sql.SQLFeatureNotSupportedException;

public class DynamicDataSource extends AbstractRoutingDataSource {
    private static Logger logger = Logger.getLogger(DynamicDataSource.class);

    protected Object determineCurrentLookupKey() {
        Object datasourceName = ContextHolder.getDataSource();
        //logger.info("current datasourceName is: " + ObjectUtils.toString(datasourceName, "idCenterDS"));
        if (datasourceName == null) {
            return "idCenterDS";
        } else {
            return datasourceName;
        }
    }
//
//    @Override
//    public java.util.logging.Logger getParentLogger() throws SQLFeatureNotSupportedException {
//        return null;
//    }
}
