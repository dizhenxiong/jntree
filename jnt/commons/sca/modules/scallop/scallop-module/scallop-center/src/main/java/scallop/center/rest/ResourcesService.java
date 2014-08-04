package scallop.center.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;
import org.json.JSONObject;

import scallop.center.dao.Resource;
import scallop.center.dao.ResourceDao;
import scallop.center.dao.ibatis.ResourceDaoIbatis;
import scallop.core.exception.ScallopServerDaoException;

@Path("resources")
public class ResourcesService {

    private static final String RESOURCE = "resource";
    private static final String NAME = "name";
    private static final String STRATEGY = "strategy";
    @GET
    @Path("{name}")
    @Produces(MediaType.TEXT_HTML)
    public String getResource(@PathParam(NAME) String name) throws ScallopServerDaoException, JSONException {
        //连接数据库获取资源文件
        ResourceDao dao = new ResourceDaoIbatis();
        Resource resource = dao.getResourceByName(name);
        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate(NAME, name);
        jsonObject.accumulate(RESOURCE, resource==null?"":resource.getResource());
        jsonObject.accumulate(STRATEGY, resource==null?"":resource.getStrategy());
        return jsonObject.toString();
    }

    @GET
    @Produces("text/html")
    public String getHtml() {
        return "<html><head></head><body>\n"
                + "welcome to  resource (as html text).\n"
                + "</body></html>";
    }
}
