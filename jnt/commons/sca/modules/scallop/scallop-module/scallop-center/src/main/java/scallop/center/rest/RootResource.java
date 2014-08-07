package scallop.center.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/")
public class RootResource {

    @GET
    @Produces("text/html")
    public String getHtml() {
        return "<html><head></head><body>\n" + "welcome to scallop resource center.\n" + "</body></html>";
    }

}
