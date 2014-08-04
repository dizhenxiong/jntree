package com.lenovo.vctl.dal.dao.id.dao;

/**
 * @author
 */
import java.sql.Types;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.lenovo.vctl.dal.dao.id.util.IdCenterHelper;
import com.lenovo.vctl.dal.dao.util.Constants;

public class SequenceDao {

    private static final Log log = LogFactory.getLog(SequenceDao.class);

    protected DataSource dataSource;

    protected SimpleJdbcTemplate simpleJdbcTemplate;

    protected SimpleJdbcInsert insertActor;
    
    protected SimpleJdbcCall proc;

    /**
     * @param db
     *            :IdCenter 鎵�敤鍒扮殑鏁版嵁婧�
     */
    public SequenceDao(DataSource db) {
        dataSource = db;
        simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
        insertActor = new SimpleJdbcInsert(dataSource).withTableName(IdCenterHelper.getTableName())
                .usingGeneratedKeyColumns("id");
        proc = new SimpleJdbcCall(dataSource)
                   .withProcedureName("getSequence")
                   .useInParameterNames("seqName")
                   .declareParameters(new SqlOutParameter(Constants.MinId,Types.BIGINT),
                                      new SqlOutParameter(Constants.MaxId,Types.BIGINT));
    }
    public Map getSeqIds(String seqName) {
          SqlParameterSource params = new MapSqlParameterSource().addValue("seqName", seqName);
          Map map = null;
          try{
              map = proc.execute(params);
          }
          catch(Exception e){
              log.warn("Warn:IdCenter Exception --> when execute stored procedure \r\n");
              e.printStackTrace(System.err);
          }
          return  map;
    }
    
    public Integer getSeqSpan(String seqName){
    	Integer span = 1;
    	try{
    	  	span = simpleJdbcTemplate.queryForInt("select span from dalsequence  where name=?",seqName);
    	}
    	catch(Exception e){
    		e.printStackTrace(System.out);
    	}
    	if(span <1 ){
    		span = 1;
    	}
    	return span;
    }

}
