package scallop.center.dao.ibatis;

import java.io.IOException;
import java.io.Reader;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

public class SqlMapperCreator {

  /**
   * SqlMapClient instances are thread safe, so you only need one.
   * In this case, we'll use a static singleton.  So sue me.  ;-)
   */
  private static SqlMapClient sqlMapper;

  static class SingletonHolder {
      static SqlMapperCreator instance = new SqlMapperCreator();
  }

  public static SqlMapperCreator getInstance() {
     return SingletonHolder.instance;
  }

  private SqlMapperCreator() {
    try {
      Reader reader = Resources.getResourceAsReader("SqlMapConfig.xml");
      sqlMapper = SqlMapClientBuilder.buildSqlMapClient(reader);
      reader.close();
    } catch (IOException e) {
      throw new RuntimeException("Something bad happened while building the SqlMapClient instance." + e, e);
    }
  }
  
  public SqlMapClient getSqlMapper() {
      return SqlMapperCreator.sqlMapper;
  }

}
