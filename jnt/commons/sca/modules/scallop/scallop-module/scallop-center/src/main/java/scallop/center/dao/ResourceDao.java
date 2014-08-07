package scallop.center.dao;

import scallop.core.exception.ScallopServerDaoException;

public interface ResourceDao {

    Resource getResourceByName(String name) throws ScallopServerDaoException;
    
}
