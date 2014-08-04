package com.lenovo.vctl.dal.dao.id.util;

import com.lenovo.vctl.dal.dao.config.helper.DaoHelper;

/**
 * @author arthurkang
 */

public class IdCenterHelper {

    // 数据库中存放 sequence 的表名
    private static final String SeqTableName = "dalSequence";
    // 用户在POJO 的类文件中制定所使用sequence,所用的参数
    private static final String SeqParamName = "sequence";
    private static final String IdCenterDatasourceName = DaoHelper.getIdCenterDsName();;

    public static String getTableName() {
        return SeqTableName;
    }

    public static String getSeqParamName() {
        return SeqParamName;
    }

    public static String getIDCenterDataSourceName() {
        return IdCenterDatasourceName;
    }

}
