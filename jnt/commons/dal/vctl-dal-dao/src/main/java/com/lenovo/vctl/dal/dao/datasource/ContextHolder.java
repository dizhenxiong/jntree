package com.lenovo.vctl.dal.dao.datasource;

public class ContextHolder {
    private static final ThreadLocal contextHolder = new ThreadLocal();

    public static void setDataSource(String datasource) {
        contextHolder.set(datasource);
    }

    public static String getDataSource() {
        return (String) contextHolder.get();
    }

    public static void clearDataSource() {
        contextHolder.remove();
    }
}
