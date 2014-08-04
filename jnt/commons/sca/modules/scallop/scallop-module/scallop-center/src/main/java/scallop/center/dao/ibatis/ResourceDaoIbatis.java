package scallop.center.dao.ibatis;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import scallop.core.exception.ScallopServerDaoException;
import scallop.center.dao.Resource;
import scallop.center.dao.ResourceDao;

import com.ibatis.sqlmap.client.SqlMapClient;

public class ResourceDaoIbatis implements ResourceDao {
    private final static Logger logger = LoggerFactory
            .getLogger(ResourceDaoIbatis.class);
    
    private SqlMapClient sqlMapper = SqlMapperCreator.getInstance().getSqlMapper();
    
    public Resource getResourceByName(String name) throws ScallopServerDaoException {
        try {
            return (Resource)sqlMapper.queryForObject("selectResourceByName", name);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
            throw new ScallopServerDaoException(e.getMessage(), e);
        }
    }

}
